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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.domain.model.Product;

@RestController
public class InventoryController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ObjectMapper mapper;
    
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

    @GetMapping("/inventory")
    public ResponseEntity<Object> listAll() {
        return null;
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<Object> delete() {
        return null;
    }

    @GetMapping("/inventory?id={id}")
    public ResponseEntity<Object> listById(@RequestParam("id") Integer id) {
        return null;
    }

}
