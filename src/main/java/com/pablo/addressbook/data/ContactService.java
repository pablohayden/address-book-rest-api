package com.pablo.addressbook.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactService {
	
	
	@Autowired
	private EntityManager entityManager;
	
	
	public Contact save(Contact contact) throws Exception {
		try {
		entityManager.persist(contact);
		
		entityManager.flush();
		}catch(Exception e) {
			throw new InvalidContactException("Invalid Contact");
		}
		return contact;
	}
	

	public List<Contact> findAll() {
		
		  TypedQuery<Contact> query =
				  entityManager.createNamedQuery("Contact.findAll", Contact.class);
			  List<Contact> results = query.getResultList();

	        
	        return results;
	}

	public List<Contact> findContactByName(String contactNamePattern) {
		
		 TypedQuery<Contact> query =
				  entityManager.createNamedQuery("Contact.findByName", Contact.class);
		 
		 		query.setParameter("name", contactNamePattern);
		 
			  List<Contact> results = query.getResultList();

	        
		return results;
	}
	
	public List<Contact> findContactsNameLike(String contactNamePattern) {
		
		 TypedQuery<Contact> query =
				  entityManager.createNamedQuery("Contact.findNameLikePattern", Contact.class);
		 
		 		query.setParameter("pattern", contactNamePattern);
		 
			  List<Contact> results = query.getResultList();

	        
		return results;
	}

	public void delete(Contact contact) {
		
		entityManager.remove(contact);
		
	}


	public List<Contact> findDistinctContactByName(String contactNamePattern) {

		 TypedQuery<Contact> query =
				  entityManager.createNamedQuery("Contact.findDistinctByName", Contact.class);
		 
		 		query.setParameter("name", contactNamePattern);
		 
			return query.getResultList();
	}


	public void update(Contact contact) {
		entityManager.merge(contact);
	}


	public Contact findById(Contact contact) {
		
		return entityManager.createNamedQuery("Contact.findById", Contact.class).setParameter("id",  contact.getId()).getSingleResult(); 		
	
	}
	
	public Contact findById(long id) {
		
		return entityManager.createNamedQuery("Contact.findById", Contact.class).setParameter("id", id).getSingleResult();
	
	}


	public void purge() {
		entityManager.createNamedQuery("Contact.purge").executeUpdate();
	}


	public AddressBook filterContacts(Filter filter) {

			String name = filter.getName();
			String pattern = "%" + name + "%";
			List<Contact> contacts2 = findContactsNameLike(pattern);	
			
		 
			return new AddressBook(contacts2);
	}
}
