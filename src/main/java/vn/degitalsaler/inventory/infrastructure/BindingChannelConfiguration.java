/*
 * Class: ChannelConfiguration
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
package vn.degitalsaler.inventory.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class BindingChannelConfiguration {

    @Bean(Channels.HTTP_INPUT_CHANNEL)
    MessageChannel getHttpInputChannel() {
        return new DirectChannel();
    }
    
    @Bean(Channels.HTTP_OUTPUT_CHANNEL)
    MessageChannel httpOutputChannel() {
        return new DirectChannel();
    }
}