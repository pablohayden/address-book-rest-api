package com.pablo.addressbook;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressRestController.class)
public class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressBookProxyService addressBookProxyService;

    private String unfilteredexpected = "{\"contacts\":[{\"name\":\"Rolling Stones\",\"phone_number\":\"09989898989\",\"address\":\"outhern France\"},{\\\"name\\\":\\\"Bob Dylan\\\",\\\"phone_number\\\":\\\"07989013937\\\",\\\"address\\\":\\\"Highway 66\\\"}]}";
	
    @Ignore
    @Before
    public void setup() {
    	
    	List<Contact> contacts = new ArrayList<Contact>();
    	
    	contacts.add(new Contact("Bob Dylan","07989013937", "Highway 66"));
    	contacts.add(new Contact("Rolling Stones","07989013937", "Exile on Main Street"));
    	
    	AddressBook addressbook = new AddressBook(contacts);
    	
        given(this.addressBookProxyService.
            getAddressBook()
        ).willReturn(addressbook);
        
      
    }
    
    @Test
    public void testAddressBookGetRequestFiltered() throws Exception {

    	ObjectMapper mapper = new ObjectMapper();
    	
    	Filter filter = new Filter("Rolling","","");
    	
    	String filteredexpected = "{\"contacts\":[{\"name\":\"Rolling Stones\",\"phone_number\":\"07989013937\",\"address\":\"Exile on Main Street\"}],\"filter\":{\"name\":\"Rolling\",\"phone\":\"\",\"address\":\"\"}}";
    			
    	//Object to JSON in String
    	String json = mapper.writeValueAsString(filter);
    	
    	System.out.println(json);

		MvcResult result = this.mockMvc.perform(post("/addressbook/").contentType(MediaType.APPLICATION_JSON_UTF8).content(json)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(filteredexpected))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andReturn();
    }
    
    @Test
    public void testAddressBookGetRequestUnfiltered() throws Exception {

    	String expected = "{\"contacts\":[{\"name\":\"Test User\",\"phone_number\":\"07989013937\",\"address\":\"Highway 66\"}]}";
		
    	
		MvcResult result = this.mockMvc.perform(get("/addressbook")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(expected))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andReturn();

    }
}