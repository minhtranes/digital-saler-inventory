/*
 * Class: ProductEventRepositoryImpl
 *
 * Created on May 9, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.infrastructure.repository.impl;

import org.springframework.stereotype.Repository;

import vn.degitalsaler.inventory.domain.Event;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;

@Repository
public class ProductEventRepositoryImpl implements ProductEventRepository{

    @Override
    public void saveProductEvent(Event<Long> event) {
        
        
        
    }

}