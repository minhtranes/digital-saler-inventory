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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.domain.ProductRequest;
import vn.degitalsaler.inventory.domain.ProductResponse;

@RestController
public class InventoryController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ObjectMapper mapper;
    
    @PutMapping("/inventory")
    public ResponseEntity<Object> add(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        final ProductRequest productRequest = new ProductRequest(productAsJson);
        final ProductResponse response = this.productService.addProduct(productRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/inventory")
    public ResponseEntity<Object> update(@RequestBody final Object product) {
        final ObjectNode productAsJson = this.mapper.convertValue(product, ObjectNode.class);
        
        final ProductRequest productRequest = new ProductRequest(productAsJson);
        final ProductResponse response = this.productService.updateProduct(productRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public ResponseEntity<Object> listAll() {
        
        final List<ProductResponse> jsonNodes = this.productService.findAll();

        return new ResponseEntity<>(jsonNodes, HttpStatus.OK);
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<Object> delete() {
        return null;
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<Object> listById(@PathVariable(value = "id", required = true) final Long id) {

        final ProductResponse response = this.productService.findProductById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}