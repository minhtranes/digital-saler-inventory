/*
 * Class: ProductHttpGatewayConfiguration
 *
 * Created on May 6, 2021
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
import org.springframework.integration.gateway.GatewayProxyFactoryBean;

import vn.degitalsaler.inventory.represenation.integrator.ProductHttpGateway;

@Configuration
public class ProductHttpGatewayConfiguration {

    @Bean("productGateway")
    public GatewayProxyFactoryBean getProductGateway() {
        return new GatewayProxyFactoryBean(ProductHttpGateway.class);
    }
    
}
