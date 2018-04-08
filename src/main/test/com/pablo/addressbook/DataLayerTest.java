package com.pablo.addressbook;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.pablo.addressbook.data.Contact;
import com.pablo.addressbook.data.ContactService;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ContactService.class)
public class DataLayerTest {
	

    @Autowired
    private ContactService addressBookService;
    
    @Before
    public void executedBeforeEach() {
    	
    	Contact contact1 = new Contact("Pablo Hayden", "0909090909", "Highway 66");
		Contact contact2 = new Contact("Ted Dancer", "0909090909", "Highway 66");
		Contact contact3 = new Contact("Jim Dandy", "0909090909", "Highway 66");
		
		try {
			addressBookService.save(contact1);
			addressBookService.save(contact2);
			addressBookService.save(contact3);
		} catch (Exception e) {
			
		}
		
		
   }

	@Test
	public void findAllContacts() {
		
		List<Contact> contacts = addressBookService.findAll();
		
		contacts.forEach(contact -> System.out.println(contact));
		
		assertTrue(contacts.size() == 3);
		
	}

	@Test
	public void findLikeNamePattern() {
		
		
		String contactNamePattern = "%Dan%";
		
		List<Contact> contacts = addressBookService.findContactsNameLike(contactNamePattern);	
			
		assertTrue(contacts.size() == 2);
		
	}
	
	@Test
	public void deleteContact() {
		
		
		String contactNamePattern = "Pablo Hayden";
		
		List<Contact> contacts = addressBookService.findContactByName(contactNamePattern);
		
		addressBookService.delete(contacts.get(0));
		
		contacts = addressBookService.findContactByName(contactNamePattern);
		
			
		assertTrue(contacts.size() == 0);
		
	}
	
	@Test
	public void updateContact() {
		
		
		String contactNamePattern = "Pablo Hayden";
		
		List<Contact> contacts = addressBookService.findDistinctContactByName(contactNamePattern);
		
		
		if(contacts.size() == 1) {
			
			Contact contact = contacts.get(0);
			
			contact.setName("Pablo Changed");
			
			addressBookService.update(contact);
			
			
			Contact updatedcontact = addressBookService.findById(contact);
			
			assertSame(contact, updatedcontact);
			
		}
		else fail("Expected 1 entity");
		
	}
}
