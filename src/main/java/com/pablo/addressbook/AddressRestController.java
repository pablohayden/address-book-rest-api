package com.pablo.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressRestController {

    @Autowired
    private AddressBookProxyService addressBookProxyService; 

    @GetMapping("/addressbook")
    @ResponseBody
    public AddressBook getAddress() {
    	
    	AddressBook addresbook = 	addressBookProxyService.getAddressBook();
    
    	return addresbook;
    }
    
    @PostMapping("/addressbook")
    @ResponseBody
    public AddressBook getAddressBookFiltered(@RequestBody Filter filter) {
    	
    	AddressBook addresbook = 	addressBookProxyService.getAddressBook();
    	
    	addresbook.applyFilter(filter);
    	
    	return addresbook;
    }
}