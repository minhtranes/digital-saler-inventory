/*
 * Class: ProductServiceImpl
 *
 * Created on May 5, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.application.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;

import vn.degitalsaler.inventory.application.exception.InventoryException;
import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.domain.ProductRequest;
import vn.degitalsaler.inventory.domain.ProductResponse;
import vn.degitalsaler.inventory.domain.event.ProductCreatedEvent;
import vn.degitalsaler.inventory.domain.event.ProductUpdatedEvent;
import vn.degitalsaler.inventory.infrastructure.properties.JsonProductProperties;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private static final String PRODUCT_STORAGE = "key-store-value";

    @Autowired
    private ProductEventRepository productEventRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private JsonProductProperties jsonProductProperties;

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Override
    public ProductResponse addProduct(final ProductRequest product) {
        final JsonNode productAsJson = product.getProduct();
        final ProductResponse response = new ProductResponse(productAsJson);
        try {
            final Map<String, Object> productAsMap = mapper.convertValue(productAsJson,
                new TypeReference<Map<String, Object>>() {
                });
            final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);
            //FIXME: check id exists before adding product
            this.validateExistingId(id);

            final ProductCreatedEvent createdEvent = new ProductCreatedEvent(id, productAsJson,
                ProductCreatedEvent.class);
            this.productEventRepository.saveProductEvent(createdEvent);
            return response;
        } catch (Exception e) {
            LOGGER.error("Error while adding a new product. Detail: ", e);
            response.setException(e);
            return response;
        }
    }

    @Override
    public List<ProductResponse> findAll() {
        final List<ProductResponse> jsonNodes = new ArrayList<>();
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            final KeyValueIterator<Long, JsonNode> range = keyValueStore.all();
            while (range.hasNext()) {
                final KeyValue<Long, JsonNode> next = range.next();
                final ProductResponse response = new ProductResponse(next.value);
                jsonNodes.add(response);
            }
        } catch (Exception e) {
            LOGGER.error("Error while finding all products. Detail: ", e);
            final ProductResponse response = new ProductResponse(null);
            response.setException(e);
            jsonNodes.clear();
            jsonNodes.add(response);
        }
        return jsonNodes;
    }

    @Override
    public ProductResponse findProductById(final Long id) {
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            final JsonNode productAsJson = keyValueStore.get(id);
            return new ProductResponse(productAsJson);
        } catch (Exception e) {
            LOGGER.error("Error while finding a product by id [{}]. Detail: ", id, e);
            final ProductResponse response = new ProductResponse(null);
            response.setException(e);
            return response;
        }
    }

    @Override
    public ProductResponse updateProduct(final ProductRequest product) {
        final JsonNode productAsJson = product.getProduct();

        final Map<String, Object> productAsMap = mapper.convertValue(product, new TypeReference<Map<String, Object>>() {
        });
        final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);

        final ProductResponse response = new ProductResponse(productAsJson);
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            final JsonNode oldProduct = keyValueStore.get(id);

            //FIXME: add a validation for null pointer cases
            this.validateExistingProduct(oldProduct, id);

            final Map<String, Object> leftMap = mapper.convertValue(oldProduct,
                new TypeReference<HashMap<String, Object>>() {
                });
            final Map<String, Object> rightMap = mapper.convertValue(product,
                new TypeReference<HashMap<String, Object>>() {
                });
            
            final MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);
            final Map<String, ValueDifference<Object>> changedValue = difference.entriesDiffering();

            if (!changedValue.isEmpty()) {
                final long createdTime = System.currentTimeMillis();

                changedValue.forEach((key, value) -> {

                    final Map<String, Object> objectNode = new HashMap<>();
                    objectNode.put(key, value.rightValue());

                    final JsonNode payload = this.mapper.convertValue(objectNode, JsonNode.class);
                    final ProductUpdatedEvent event = new ProductUpdatedEvent(id, payload, createdTime,
                        ProductUpdatedEvent.class);

                    this.productEventRepository.saveProductEvent(event);
                });
                return response;
            } else {
                // TODO: handling business exception
                throw new InventoryException(String.format("the product id [%d] doesn't change anything", id));
            }
        } catch (Exception e) {
            LOGGER.error("Error while updating a product by id [{}]. Detail: ", id, e);
            response.setException(e);
            return response;
        }
    }

    private void validateExistingProduct(final JsonNode oldProduct,final Long id) throws InventoryException {
        if(oldProduct == null) {
            throw new InventoryException(String.format("The product id is [%d] not exist", id));
        }
    }
    
    private void validateExistingId(Long id) throws InventoryException {
        if(id == null) {
            throw new InventoryException(String.format("The product id is [%d] null", id));
        }
    }
}