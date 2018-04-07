package com.pablo.addressbook.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQueries({
    @NamedQuery(name="Contact.findAll",
                query="SELECT c FROM Contact c"),
    @NamedQuery(name="Contact.findByName",
                query="SELECT c FROM Contact c WHERE c.name = :name"),
    @NamedQuery(name="Contact.findById",
    			query="SELECT c FROM Contact c WHERE c.id = :id"),
    @NamedQuery(name="Contact.findDistinctByName",
    			query="SELECT DISTINCT(c) FROM Contact c WHERE c.name = :name"),
    @NamedQuery(name="Contact.findNameLikePattern",
    			query="SELECT c FROM Contact c WHERE c.name LIKE :pattern"),
    @NamedQuery(name="Contact.purge",
    			query="DELETE from Contact")
    
})
public class Contact {
	
		public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long id;
		
		@Column
		@JsonProperty("name")
		private String name;
		
		@JsonProperty("phone_number")
		private String phoneNumber;
		
		@JsonProperty("address")
		private String address;
		
		public Contact(String name, String phoneNumber, String address) {
			this.name=name;
			this.phoneNumber=phoneNumber;
			this.address=address;
		}
		
		public Contact() {
			
		}
		@Override
		public String toString() {
			return "Contact [name: " + name + ", phone-number:" + phoneNumber + ", address: " +  address + "]";
		}

}
