package com.pablo.addressbook.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressBook {
	
	private List<Contact> contacts = new ArrayList<Contact>();
	
	@Transient
	private Filter filter;
	
	public AddressBook() {
	}

	public AddressBook(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public Filter getFilter() {
		return filter;
	}

	@JsonIgnore
	public void applyFilter(Filter filter) {
		this.filter = filter;
		
		String regex = "(?i:.*" + filter.getName() + ".*)";
    	
    	this.contacts = this.contacts.stream()
    			.filter(contact -> contact.getName().matches(regex))    
                .collect(Collectors.toList());  
    	

	}

}
