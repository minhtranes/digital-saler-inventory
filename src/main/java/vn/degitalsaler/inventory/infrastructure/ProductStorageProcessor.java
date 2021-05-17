/*
 * Class: ProductIntegrationProcessor
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
package vn.degitalsaler.inventory.infrastructure;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import vn.degitalsaler.inventory.domain.event.Event;

@Configuration
public class ProductStorageProcessor {

    private ObjectMapper mapper;
    
    public ProductStorageProcessor(ObjectMapper mapper) {
        super();
        this.mapper = mapper;
    }

    @Bean
    public Consumer<KStream<Long, Event<Long>>> productEventProcessor() {

        return input -> input.map((key, value) -> KeyValue.pair(key, value.getPayload())).groupByKey(
            Grouped.with(Serdes.Long(), new JsonSerde<>(JsonNode.class))).reduce((oldValue, newValue) -> {

                final Map<String, Object> oldValueAsMap = mapper.convertValue(oldValue,
                    new TypeReference<Map<String, Object>>() {
                });
                final Map<String, Object> newValueAsMap = mapper.convertValue(newValue,
                    new TypeReference<Map<String, Object>>() {
                    });
                final String changedKey = newValueAsMap.entrySet().stream().collect(Collectors.toList()).get(0).getKey();

                final Object changedValue = JsonPath.parse(newValueAsMap).read(String.format("$.%s", changedKey), JsonNode.class);
                
                final DocumentContext documentContext = JsonPath.parse(oldValueAsMap).set(changedKey, changedValue);
                
                return mapper.convertValue(documentContext.json(), JsonNode.class);

            }, Named.as("product-storage"), Materialized.as("key-store-value"));
    }
}