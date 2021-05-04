/*
 * Class: ProductCommand
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

abstract class Command<T> {

    T commandId;
    
    long createdTime;

    public Command(T commandId) {
        super();
        this.commandId = commandId;
    }

    public T getCommandId() {
        return this.commandId;
    }

    public long getCreatedTime() {
        return this.createdTime;
    }
}