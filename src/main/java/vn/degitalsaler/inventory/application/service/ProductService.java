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

import vn.degitalsaler.inventory.domain.ProductRequest;
import vn.degitalsaler.inventory.domain.ProductResponse;

public interface ProductService {

    ProductResponse addProduct(ProductRequest product);
    
    ProductResponse updateProduct(ProductRequest product);
    
    List<ProductResponse> findAll();
    
    ProductResponse findProductById(Long id);
    
}