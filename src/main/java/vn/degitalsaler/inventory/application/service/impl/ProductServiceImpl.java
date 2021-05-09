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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;
import vn.degitalsaler.inventory.infrastructure.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductEventRepository productEventRepository;
    
    @Override
    public JsonNode addProduct(JsonNode product) {
        try {
            this.productRepository.saveProduct(product);
        }
        catch (Exception e) {
        }
        return product;
    }

    @Override
    public List<JsonNode> findAll() {
     // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonNode findProductById(Long id) {
     // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonNode updateProduct(JsonNode product) {
        // TODO Auto-generated method stub
        return null;
    }

}