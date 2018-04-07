package com.pablo.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients
@EnableSwagger2
@SpringBootApplication
public class AddressBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressBookApplication.class, args);
	}


	
	
		
//		@Bean
//		public CommandLineRunner demo(AddressBookRepository repository) {
//			return (args) -> {
				// save a couple of customers
				
//				
//				repository.save(new Contact("Jack", "Bauer", ""));
//				repository.save(new Contact("Chloe", "O'Brian"));
//				repository.save(new Contact("Kim", "Bauer"));
//				repository.save(new Contact("David", "Palmer"));
//				repository.save(new Contact("Michelle", "Dessler"));
//
//				// fetch all customers
//				log.info("Customers found with findAll():");
//				log.info("-------------------------------");
//				for (Customer customer : repository.findAll()) {
//					log.info(customer.toString());
//				}
//				log.info("");
//
//				// fetch an individual customer by ID
//				repository.findById(1L)
//					.ifPresent(customer -> {
//						log.info("Customer found with findById(1L):");
//						log.info("--------------------------------");
//						log.info(customer.toString());
//						log.info("");
//					});
//
//				// fetch customers by last name
//				log.info("Customer found with findByLastName('Bauer'):");
//				log.info("--------------------------------------------");
//				repository.findByLastName("Bauer").forEach(bauer -> {
//					log.info(bauer.toString());
//				});
//				// for (Customer bauer : repository.findByLastName("Bauer")) {
//				// 	log.info(bauer.toString());
//				// }
//				log.info("");
//			};
//		}
	
}
