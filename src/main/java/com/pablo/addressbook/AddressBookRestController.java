package com.pablo.addressbook;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pablo.addressbook.data.AddressBook;
import com.pablo.addressbook.data.Contact;
import com.pablo.addressbook.data.ContactService;
import com.pablo.addressbook.data.Filter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
public class AddressBookRestController {
	
	@Autowired
	private static Logger LOG = LoggerFactory.getLogger(AddressBookRestController.class);
	 
    @Autowired
    private AddressBookRestProxyService addressBookProxyService; 


    @Autowired
    private ContactService contactService; 
    
    @GetMapping("/addressbook/reload")
    @ResponseBody
    public AddressBook initializeAddress() {
    	
    	AddressBook addressbook = 	addressBookProxyService.getAddressBook();
    	
    	contactService.purge();
    	
    	addressbook.getContacts().forEach(contact ->{
    		try {
				contactService.save(contact);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	});
    	
    
    	return addressbook;
    }
    
    @GetMapping("/addressbook")
    @ResponseBody
    public AddressBook getAddressBook() {
    	
    	List<Contact> contacts = contactService.findAll();
    	
    	return new AddressBook(contacts);
     }
    
    @PostMapping("/addressbook/filter")
    @ResponseBody
    public AddressBook getAddressBookFiltered(@RequestBody Filter filter) {
    	
    	AddressBook addresbook = contactService.filterContacts(filter);
    	
    	addresbook.applyFilter(filter);
    	
    	return addresbook;
    }
    
    @PostMapping("/addressbook")
    @ResponseBody
    public ResponseEntity<?> createContact(@RequestBody Contact contact) {
    	
    	List<Contact> contacts = contactService.findContactByName(contact.getName());
  	  
    	if (!contacts.isEmpty()){

    		LOG.info("Unable to create user as name '{}' already exists", contact.getName());
            
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
         }

          Contact newcontact;
			try {
				newcontact = contactService.save(contact);
				   URI location = ServletUriComponentsBuilder
			  				.fromCurrentRequest().path("/{id}")
			  				.buildAndExpand(newcontact.getId()).toUri();
			          
			          return ResponseEntity.created(location).build();
			} catch (Exception e) {
				LOG.info("Unable to save entity {}", contact.getName());
			}
          
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        
    }
    
    @PutMapping("/addressbook")
    @ResponseBody
    public ResponseEntity<?> updateContact(@RequestBody Contact contact) {
     	
    	contactService.update(contact);
    	
    	URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(contact.getId()).toUri();
    			
    	return ResponseEntity.created(location).build(); 

    }
  
    @DeleteMapping("/addressbook/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteContact(@PathVariable long id) {
    	
    	
    	Contact contact = contactService.findById(id);
    	  
    	if (contact == null){

    		LOG.info("Unable to delete. User with id {} not found", id);
            
    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
         }

          contactService.delete(contact);
          

          
          return new ResponseEntity<Void>(HttpStatus.OK);
      }
}