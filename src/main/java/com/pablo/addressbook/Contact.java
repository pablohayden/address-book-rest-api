package com.pablo.addressbook;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {
	
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
		
}
