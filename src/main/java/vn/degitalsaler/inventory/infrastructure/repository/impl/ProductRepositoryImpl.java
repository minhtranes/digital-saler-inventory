/*
 * Class: ProductRepositoryImpl
 *
 * Created on May 7, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.infrastructure.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;

import vn.degitalsaler.inventory.infrastructure.repository.ProductRepository;

@Repository("productRepository")
public class ProductRepositoryImpl implements ProductRepository{

    @Autowired
    private StreamBridge streamBridge;
    
    @Override
    public JsonNode saveProduct(JsonNode product) {
        
        Message<JsonNode> message = MessageBuilder.withPayload(product)
                .setHeader(KafkaHeaders.MESSAGE_KEY, 12345L)
                .build();
        
        this.streamBridge.send("product-storage-out", message);
        
        return null;
    }

}