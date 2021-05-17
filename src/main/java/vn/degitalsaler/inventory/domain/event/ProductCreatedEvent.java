/*
 * Class: ProductCreatedEvent
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
package vn.degitalsaler.inventory.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class ProductCreatedEvent extends Event<Long> {

    @JsonProperty
    private JsonNode payload;
    
    public ProductCreatedEvent(Long eventId, JsonNode payload, Class<?> clazz) {
        super(eventId, clazz);
        this.payload = payload;
        this.createdTime = System.currentTimeMillis();
    }

    public ProductCreatedEvent() {
        super();
    }

    /**
     * Gets the payload. The root payload
     *
     * @return the payload
     */
    public JsonNode getPayload() {
        return this.payload;
    }
}