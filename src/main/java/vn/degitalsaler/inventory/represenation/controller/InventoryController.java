/*
 * Class: ProductController
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
package vn.degitalsaler.inventory.represenation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @PostMapping("/inventory")
    public ResponseEntity<Object> create(@RequestBody final Object product) {
        return null;
    }

    @GetMapping("/inventory")
    public ResponseEntity<Object> listAll() {
        return null;
    }

    @DeleteMapping("/inventory")
    public ResponseEntity<Object> delete() {
        return null;
    }

    @GetMapping("/inventory")
    public ResponseEntity<Object> listById(@RequestParam("id") Integer id) {
        return null;
    }

}
