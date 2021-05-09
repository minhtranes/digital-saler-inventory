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
package vn.degitalsaler.inventory.domain;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductCreatedEvent extends Event<Long> {

    private JsonNode payload;
    
    public ProductCreatedEvent(Long eventId, JsonNode payload) {
        super(eventId);
        this.payload = payload;
        this.createdTime = System.currentTimeMillis();
    }

    public JsonNode getPayload() {
        return this.payload;
    }

    public void setPayload(JsonNode product) {
        this.payload = product;
    }
}