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
    
    public ProductUpdatedEvent(Long eventId, JsonNode payload, Class<?> clazz) {
        super(eventId, clazz);
        this.createdTime = System.currentTimeMillis();
        this.payload = payload;
    }
    
    public ProductUpdatedEvent(Long eventId, JsonNode payload, Long createdTime, Class<?> clazz) {
        super(eventId, clazz);
        this.createdTime = createdTime;
        this.payload = payload;
    }
  
    public ProductUpdatedEvent() {
        super();
    }

    /**
     * Gets the payload. Include the updated key and value
     *
     * @return the payload
     */
    public JsonNode getPayload() {
        return this.payload;
    }
}