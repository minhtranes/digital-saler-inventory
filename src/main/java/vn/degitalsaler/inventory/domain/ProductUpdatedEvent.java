/*
 * Class: ProductUpdateEvent
 *
 * Created on May 4, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.domain;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductUpdatedEvent extends Event<Long>{

    private JsonNode updatedValue;
    
    private String jsonPath;
    
    public ProductUpdatedEvent(Long eventId) {
        super(eventId);
        this.createdTime = System.currentTimeMillis();
    }

    public JsonNode getUpdatedValue() {
        return this.updatedValue;
    }

    public void setUpdatedValue(JsonNode value) {
        this.updatedValue = value;
    }

    public String getJsonPath() {
        return this.jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }
}