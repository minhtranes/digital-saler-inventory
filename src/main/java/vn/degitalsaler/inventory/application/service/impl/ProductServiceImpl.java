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

import vn.degitalsaler.inventory.application.service.ProductService;
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
    public void addProduct(final JsonNode product) {
        try {
            //FIXME: check id exists before adding product
            final Map<String, Object> productAsMap = mapper.convertValue(product,
                new TypeReference<Map<String, Object>>() {
                });
            final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);

            final ProductCreatedEvent createdEvent = new ProductCreatedEvent(id, product, ProductCreatedEvent.class);
            this.productEventRepository.saveProductEvent(createdEvent);
        } catch (Exception e) {
            LOGGER.error("Error while adding a new product. Detail: ", e);
        }
    }

    @Override
    public List<JsonNode> findAll() {
        final List<JsonNode> jsonNodes = new ArrayList<>();
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            final KeyValueIterator<Long, JsonNode> range = keyValueStore.all();
            while (range.hasNext()) {
                KeyValue<Long, JsonNode> next = range.next();
                jsonNodes.add(next.value);
            }
        } catch (Exception e) {
            LOGGER.error("Error while finding all products. Detail: ", e);
        }
        return jsonNodes;
    }

    @Override
    public JsonNode findProductById(final Long id) {
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            return keyValueStore.get(id);
        } catch (Exception e) {
            LOGGER.error("Error while finding a product by id [{}]. Detail: ", id, e);
            return null;
        }
    }

    @Override
    public JsonNode updateProduct(final JsonNode product) {
        final Map<String, Object> productAsMap = mapper.convertValue(product, new TypeReference<Map<String, Object>>() {
        });
        final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);
        try {
            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                PRODUCT_STORAGE, QueryableStoreTypes.keyValueStore());

            final JsonNode oldProduct = keyValueStore.get(id);

            final Map<String, Object> leftMap = mapper.convertValue(oldProduct,
                new TypeReference<HashMap<String, Object>>() {
                });
            final Map<String, Object> rightMap = mapper.convertValue(product,
                new TypeReference<HashMap<String, Object>>() {
                });

            //FIXME: add a validation for null pointer cases

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
                return product;
            } else {
                // TODO: handling business exception
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error while updating a product by id [{}]. Detail: ", id, e);
            return product;
        }
    }
}