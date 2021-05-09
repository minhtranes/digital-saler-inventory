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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;

import kafka.utils.Json;

import com.google.common.collect.Maps;

import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.domain.ProductCreatedEvent;
import vn.degitalsaler.inventory.domain.ProductUpdatedEvent;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;
import vn.degitalsaler.inventory.infrastructure.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductEventRepository productEventRepository;
    
    @Autowired
    private ObjectMapper mapper;

    @Override
    public JsonNode addProduct(final JsonNode product) {
        try {
            this.productRepository.saveProduct(product);

            //FIXME: need to create strategy id for event id
            final ProductCreatedEvent createdEvent = new ProductCreatedEvent(0L, product);
            this.productEventRepository.saveProductEvent(createdEvent);
        }
        catch (Exception e) {
        }
        return product;
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
            JsonNode oldProduct = this.productRepository.saveProduct(product);

            final Map<String, Object> leftMap = mapper.convertValue(oldProduct, new TypeReference<HashMap<String, Object>>() {});
            final Map<String, Object> rightMap  = mapper.convertValue(product, new TypeReference<HashMap<String, Object>>() {});
            
            final MapDifference<String, Object> difference = Maps.difference(leftMap, rightMap);
            final Map<String, ValueDifference<Object>> changedValue = difference.entriesDiffering();

            if(changedValue.isEmpty()) {
                final List<JsonNode> payloads = new ArrayList<>();
                
                changedValue.forEach((key, value) -> {
                    
                    final Map<String, Object> objectNode = new HashMap<>();
                    objectNode.put(key, value.rightValue());
                    
                    JsonNode payload = this.mapper.convertValue(objectNode, JsonNode.class);
                    payloads.add(payload);
                });
                
                //FIXME: need to create strategy id for event id
                final ProductUpdatedEvent event = new ProductUpdatedEvent(0L, payloads);
                this.productEventRepository.saveProductEvent(event);
            }
            else {
                
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}