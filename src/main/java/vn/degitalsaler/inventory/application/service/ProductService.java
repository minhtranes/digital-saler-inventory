/*
 * Class: ProductService
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
package vn.degitalsaler.inventory.application.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface ProductService {

    void addProduct(JsonNode product);
    
    JsonNode updateProduct(JsonNode product);
    
    List<JsonNode> findAll();
    
    JsonNode findProductById(Long id);
    
}