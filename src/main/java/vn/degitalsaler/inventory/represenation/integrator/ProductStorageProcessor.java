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
package vn.degitalsaler.inventory.represenation.integrator;

import java.util.function.Consumer;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.JsonNode;

@Configuration
public class ProductStorageProcessor {

    @Bean
    public Consumer<KStream<Long, JsonNode>> process() {
        return input -> input.groupByKey().reduce((oldValue, newValue) -> newValue,
            Materialized.as("CountsKeyValueStore"));
    }
    
//      @Bean
//      public Consumer<JsonNode> process() {
//          return System.out::println;
//      }
      
}