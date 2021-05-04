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
package vn.degitalsaler.inventory.domain.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Product {
    
    private Long id;

    private String productId;

    private String importDateTime;

    private String name;

    private Double price;

    private int quantity = 1;

    private String unit;

    private double discount;

    private String tag;

    private String image;

    private String supplierInfo;
    
    private JsonNode jsonNode;
    
    public Product(JsonNode jsonNode) {
        super();
        this.jsonNode = jsonNode;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getImportDateTime() {
        return this.importDateTime;
    }

    public void setImportDateTime(String importDateTime) {
        this.importDateTime = importDateTime;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSupplierInfo() {
        return this.supplierInfo;
    }

    public void setSupplierInfo(String supplierInfo) {
        this.supplierInfo = supplierInfo;
    }

    public JsonNode getJsonNode() {
        return this.jsonNode;
    }

    public void setJsonNode(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }
} 