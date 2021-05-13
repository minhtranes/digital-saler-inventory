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

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    
    @PostMapping("/inventory")
    public ResponseEntity<Object> add(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        this.productService.addProduct(productAsJson);

        return ResponseEntity.ok("200");

//        try {
//            
//
//        }
//        catch (Exception e) {
//            // TODO: handle exception
//            
//        }
    }
    
    @PostMapping("update/inventory")
    public ResponseEntity<Object> update(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        this.productService.addProduct(productAsJson);

        return ResponseEntity.ok("200");

    }

    @GetMapping("/inventory")
    public ResponseEntity<Object> listAll() {
        return null;
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<Object> delete() {
        return null;
    }

    @GetMapping("/inventorys")
    public ResponseEntity<Object> listById(@RequestParam("id") Long id) {
        ReadOnlyKeyValueStore<Long, Object> keyValueStore = this.interactiveQueryService
            .getQueryableStore("key-store-value",
                QueryableStoreTypes.keyValueStore());
        
        System.out.println("count for hello:" + keyValueStore.get(id));

        KeyValueIterator<Long, Object> range = keyValueStore.all();
        while (range.hasNext()) {
          KeyValue<Long, Object> next = range.next();
          System.out.println("count for " + next.key + ": " + next.value);
        }
        
        return null;
    }

}
