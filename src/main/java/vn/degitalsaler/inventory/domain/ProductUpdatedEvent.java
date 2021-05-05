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

    private JsonNode payload;
    
    private String jsonPath;
    
    public ProductUpdatedEvent(Long eventId, String jsonPath, JsonNode updatedValue) {
        super(eventId);
        this.createdTime = System.currentTimeMillis();
        this.jsonPath = jsonPath;
        this.payload = updatedValue;
    }
  
    /**
     * Gets the payload. Include the updated key and value
     *
     * @return the payload
     */
    public JsonNode getPayload() {
        return this.payload;
    }

    
    /**
     * Gets the json path. Used to detect the update key in json model
     *
     * @return the json path
     */
    public String getJsonPath() {
        return this.jsonPath;
    }
}