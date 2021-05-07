/*
 * Class: Product
 *
 * Created on May 3, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.domain.json;

import com.fasterxml.jackson.databind.JsonNode;

import vn.degitalsaler.inventory.infrastructure.properties.JsonProductProperties;

public class JsonProduct {
    
    private JsonNode jsonNode;
    
    private JsonProductProperties jsonProduct;
    
    public JsonProduct(JsonNode jsonNode, JsonProductProperties jsonProduct) {
        super();
        this.jsonNode = jsonNode;
        this.jsonProduct = jsonProduct;
    }

    public String getImage() {
        return null;
    }

    public String getSupplierInfo() {
        return null;
    }
} 