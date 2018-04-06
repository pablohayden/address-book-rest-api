package com.pablo.addressbook;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="addressbook", url="${com.addressbook.url}")
public interface AddressBookProxyService {
	
    @RequestMapping(method = RequestMethod.GET, value = "/v2/581335f71000004204abaf83")
    AddressBook getAddressBook();

}