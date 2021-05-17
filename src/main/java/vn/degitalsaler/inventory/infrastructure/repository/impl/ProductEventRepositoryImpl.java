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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Repository;

import vn.degitalsaler.inventory.domain.event.Event;
import vn.degitalsaler.inventory.infrastructure.repository.ProductEventRepository;

@Repository("productEventRepository")
public class ProductEventRepositoryImpl implements ProductEventRepository{

    @Autowired
    private StreamBridge streamBridge;
    
    @Override
    public void saveProductEvent(Event<Long> event) {
        
        final Message<Event<Long>> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.MESSAGE_KEY,
            event.getEventId()).build();

        this.streamBridge.send("product-event-storage-out", message);
    }

}