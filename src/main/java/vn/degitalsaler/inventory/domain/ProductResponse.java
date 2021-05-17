/*
 * Class: ProductResponse
 *
 * Created on May 17, 2021
 *
 * (c) Copyright Swiss Post Solutions Ltd, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.domain;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductResponse {

    private JsonNode product;
    
    private Exception exception;

    public ProductResponse(JsonNode product) {
        super();
        this.product = product;
    }

    public JsonNode getProduct() {
        return product;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
    
}