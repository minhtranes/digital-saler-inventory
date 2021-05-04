/*
 * Class: JsonConfiguration
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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import vn.degitalsaler.inventory.infrastructure.properties.JsonProductProperties;

@ConfigurationProperties
public class JsonConfiguration {

    @Bean("jsonProductProperties")
    @ConfigurationProperties(value = "json.model.product")
    JsonProductProperties getJsonProductProperties() {
        return new JsonProductProperties();
    }
    
}
