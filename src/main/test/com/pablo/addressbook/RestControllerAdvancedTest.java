package com.pablo.addressbook;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.addressbook.data.Contact;
import com.pablo.addressbook.data.ContactService;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressBookRestController.class)
//@ContextConfiguration(classes={ApplicationConfig.class})
public class RestControllerAdvancedTest {

    @Autowired
    private MockMvc mockMvc;
	
	private static Logger LOG = LoggerFactory.getLogger(RestControllerAdvancedTest.class);
	
    @MockBean
    private AddressBookRestProxyService addressBookProxyService;

    @MockBean
    private ContactService contactService;
   
    
    private ObjectMapper jsonmapper = new ObjectMapper();
	  
    List<Contact> contacts = new ArrayList<Contact>();
	
    @Before
    public void setup() {
    	
    	Contact contact1 = new Contact("Bob Dylan","07989013937", "Highway 66");
    	Contact contact2 = new Contact("Mick Jagger","07989013937", "Highway 66");
    	
    	contact1.setId(new Long(1));
    	contact2.setId(new Long(2));
    	
    	contacts.add(contact1);
    	contacts.add(contact2);
    		
    }
    
    @Ignore
    @Test
    public void updateContact() throws Exception {
    	
    	Contact contact1 = contacts.get(0);
    	Contact contact2 = contacts.get(1);
    	
    	String contactjson1 = jsonmapper.writeValueAsString(contact1);
    	
    	/* Test OK delete scenario */
    	when(contactService.findContactByName(contact1.getName())).thenReturn(contacts);
        
    	this.mockMvc.perform(post("/addressbook").contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson1)).andDo(print())
		.andExpect(status().isConflict());
    	
    	contact2.setId(new Long(3));
    	/* Test OK delete scenario */
    	when(contactService.findContactByName("Jimmy")).thenReturn(new ArrayList<Contact>());
    	
    	Contact contact = contacts.get(0);
    	
    	String contactjson = jsonmapper.writeValueAsString(contact);

		this.mockMvc.perform(put("/addressbook",contact.getId()).contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson)).andDo(print())
				.andExpect(status().isCreated());
    }
    
    @Ignore
    @Test
    public void deleteContactOkAndNotFound() throws Exception {
    		
    	Contact contact = contacts.get(0);
    	
    	String contactjson = jsonmapper.writeValueAsString(contact);
    	
    	/* Test OK delete scenario */
    	when(contactService.findById(new Long(1))).thenReturn(contacts.get(0));
        
		this.mockMvc.perform(delete("/addressbook/{id}", 1).contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson)).andDo(print())
				.andExpect(status().isOk());
		
		
		/* Test NOT FOUND delete scenario */
		when(contactService.findById(new Long(2))).thenReturn(null);
    		
		this.mockMvc.perform(delete("/addressbook/{id}", 2).contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson)).andDo(print())
				.andExpect(status().isNotFound());
    }
    
// @Ignore
    @Test
    public void createContactOkAndConflict() throws Exception {
    	
     	
    	Contact contact1 = contacts.get(0);
    	Contact contact2 = contacts.get(1);
    	
    	String contactjson1 = jsonmapper.writeValueAsString(contact1);
    	String contactjson2 = jsonmapper.writeValueAsString(contact2);
    	
    	/* Test OK delete scenario */
    	when(contactService.findContactByName(contact1.getName())).thenReturn(contacts);
        
    	this.mockMvc.perform(post("/addressbook").contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson1)).andDo(print())
		.andExpect(status().isConflict());
    	
    	contact2.setId(new Long(3));
    	/* Test OK delete scenario */
    	when(contactService.findContactByName("Jimmy")).thenReturn(new ArrayList<Contact>());
    	when(contactService.save(any(Contact.class))).thenReturn(contact2);
    	this.mockMvc.perform(post("/addressbook").contentType(MediaType.APPLICATION_JSON_UTF8).content(contactjson2)).andDo(print())
		.andExpect(status().isCreated());
		
    }
    
}