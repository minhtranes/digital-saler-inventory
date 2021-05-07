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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.degitalsaler.inventory.application.service.ProductService;
import vn.degitalsaler.inventory.domain.model.Product;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private StreamBridge streamBridge;
    
    @Autowired
    private ObjectMapper mapper;

    @Override
    public Product addProduct(Product product) {
        this.streamBridge.send("product-storage-out", product);
        return product;
    }

}