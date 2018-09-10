package com.reece.addressbook;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reece.addressbook.model.AddressBook;
import com.reece.addressbook.model.Contact;

public class Junit4AddressBookTest {

	private AddressBook addressbook;

	/**
	 * Setup initial records before each step
	 * 
	 * @throws Exception
	 */
	@Before
	public void before() throws Exception {

		addressbook = new AddressBook("Manager");

		addressbook.addContact(new Contact("Mario", "0425 7657675"));
		addressbook.addContact(new Contact("Ian", "0425 78976548"));
		addressbook.addContact(new Contact("Daniel", "078 8769878"));
	}

	/**
	 * Validate contacts count
	 */
	@Test
	public void Validate_Contact_Counts() {

		assertEquals(3, addressbook.getContacts().size());
	}

	/**
	 * Add contact to address book
	 */

	@Test
	public void Add_Contact_To_AddressBook() {

		addressbook.addContact(new Contact("Todd", "0435 767436776"));

		assertEquals(4, addressbook.getContacts().size());
		assertTrue(addressbook.getContacts().contains(new Contact("Todd")));
	}

	/**
	 * Delete contact from address book
	 */
	@Test
	public void Remove_Contact_From_AddressBook() {

		addressbook.removeContact(new Contact("Mario"));

		assertEquals(2, addressbook.getContacts().size());
		assertFalse(addressbook.getContacts().contains(new Contact("Mario")));
	}

	@After
	public void teardown() throws Exception {
		addressbook = null;
	}

}
