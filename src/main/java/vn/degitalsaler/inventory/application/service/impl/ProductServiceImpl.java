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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
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
import vn.degitalsaler.inventory.domain.ProductCreatedEvent;
import vn.degitalsaler.inventory.domain.ProductUpdatedEvent;
import vn.degitalsaler.inventory.infrastructure.properties.JsonProductProperties;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;

@Service("productService")
public class ProductServiceImpl implements ProductService {

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
            final Map<String, Object> productAsMap = mapper.convertValue(product,
                new TypeReference<Map<String, Object>>() {
                });
            final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);

            final ProductCreatedEvent createdEvent = new ProductCreatedEvent(id, product, ProductCreatedEvent.class);
            this.productEventRepository.saveProductEvent(createdEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JsonNode> findAll() {
        return null;
    }

    @Override
    public JsonNode findProductById(final Long id) {
        return null;
    }

    @Override
    public JsonNode updateProduct(final JsonNode product) {
        try {
            final Map<String, Object> productAsMap = mapper.convertValue(product,
                new TypeReference<Map<String, Object>>() {
                });
            final Long id = JsonPath.parse(productAsMap).read(this.jsonProductProperties.getIdPath(), Long.class);

            final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService.getQueryableStore(
                "key-store-value", QueryableStoreTypes.keyValueStore());

            JsonNode oldProduct = keyValueStore.get(id);

            final Map<String, Object> leftMap = mapper.convertValue(oldProduct,
                new TypeReference<HashMap<String, Object>>() {
                });
            final Map<String, Object> rightMap = mapper.convertValue(product,
                new TypeReference<HashMap<String, Object>>() {
                });

            final MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);
            final Map<String, ValueDifference<Object>> changedValue = difference.entriesDiffering();

            if (changedValue.isEmpty()) {
                final long createdTime = System.currentTimeMillis();

                changedValue.forEach((key, value) -> {

                    final Map<String, Object> objectNode = new HashMap<>();
                    objectNode.put(key, value.rightValue());

                    final JsonNode payload = this.mapper.convertValue(objectNode, JsonNode.class);
                    final ProductUpdatedEvent event = new ProductUpdatedEvent(id, payload, createdTime,
                        ProductUpdatedEvent.class);

                    this.productEventRepository.saveProductEvent(event);
                });
            } else {
                // TODO: handling exception
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}