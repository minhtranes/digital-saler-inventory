/*
 * Class: InventoryException
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
package vn.degitalsaler.inventory.application.exception;

public class InventoryException extends Exception {

    private static final long serialVersionUID = -8681115131949894655L;

    public InventoryException() {
    }

    public InventoryException(final String message) {
        super(message);
    }
}