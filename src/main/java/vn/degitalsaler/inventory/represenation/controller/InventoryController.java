/*
 * Class: ProductController
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
package vn.degitalsaler.inventory.represenation.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import vn.degitalsaler.inventory.application.service.ProductService;

@RestController
public class InventoryController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private InteractiveQueryService interactiveQueryService;
    
    @PutMapping("/inventory")
    public ResponseEntity<Object> add(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        this.productService.addProduct(productAsJson);

        return new ResponseEntity<>(productAsJson, HttpStatus.OK);
    }
    
    @PostMapping("inventory")
    public ResponseEntity<Object> update(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        this.productService.updateProduct(productAsJson);

        return new ResponseEntity<>(productAsJson, HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public ResponseEntity<Object> listAll() {
        
        final List<JsonNode> jsonNodes = new ArrayList<>();
        
        final ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService
                .getQueryableStore("key-store-value",
                    QueryableStoreTypes.keyValueStore());
        
        final KeyValueIterator<Long, JsonNode> range = keyValueStore.all();
        while (range.hasNext()) {
            KeyValue<Long, JsonNode> next = range.next();
            jsonNodes.add(next.value);
        }
        return new ResponseEntity<>(jsonNodes, HttpStatus.OK);
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<Object> delete() {
        return null;
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Object> listById(@PathVariable(value = "id", required = false) final Long id) {
        ReadOnlyKeyValueStore<Long, JsonNode> keyValueStore = this.interactiveQueryService
            .getQueryableStore("key-store-value",
                QueryableStoreTypes.keyValueStore());
        
        JsonNode jsonNode = keyValueStore.get(id);

        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }

}
