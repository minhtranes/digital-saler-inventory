/*
 * Class: Supplier
 *
 * Created on May 7, 2021
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package vn.degitalsaler.inventory.domain.model;

public class SupplierInfo {

    private Long supplierId;
    
    private String supplierName;

    public SupplierInfo(Long supplierId, String supplierName) {
        super();
        this.supplierId = supplierId;
        this.supplierName = supplierName;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public String getSupplierName() {
        return this.supplierName;
    }
    
}