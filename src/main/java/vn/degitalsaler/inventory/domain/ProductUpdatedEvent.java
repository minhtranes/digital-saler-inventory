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

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductUpdatedEvent extends Event<Long>{

    private List<JsonNode> payload;
    
    public ProductUpdatedEvent(Long eventId, List<JsonNode> updatedValue) {
        super(eventId);
        this.createdTime = System.currentTimeMillis();
        this.payload = updatedValue;
    }
  
    /**
     * Gets the payload. Include the updated key and value
     *
     * @return the payload
     */
    public List<JsonNode> getPayload() {
        return this.payload;
    }
}