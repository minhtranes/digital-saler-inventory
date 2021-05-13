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
package vn.degitalsaler.inventory.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "clazz")
@JsonSubTypes({ @JsonSubTypes.Type(value = ProductCreatedEvent.class, name = "ProductCreatedEvent"),
    @JsonSubTypes.Type(value = ProductUpdatedEvent.class, name = "ProductUpdatedEvent") })
public abstract class Event<T extends Serializable>{

    T eventId;

    long createdTime;

    Class<?> clazz;
    
    public Event() {
        super();
    }

    public Event(T eventId, Class<?> clazz) {
        super();
        this.eventId = eventId;
        this.clazz = clazz;
    }

    public T getEventId() {
        return this.eventId;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }

    public abstract JsonNode getPayload();

}