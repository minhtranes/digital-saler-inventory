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

import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import vn.degitalsaler.inventory.domain.Event;

public class ProductProcessor {

    public Function<Event<Long>, JsonNode> process() {
        return null;
    }
    
}