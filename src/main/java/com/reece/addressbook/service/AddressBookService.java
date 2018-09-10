package com.reece.addressbook.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.reece.addressbook.exception.AddressBookNotFoundException;
import com.reece.addressbook.exception.ContactNotFoundException;
import com.reece.addressbook.model.AddressBook;
import com.reece.addressbook.model.Contact;

/**
 * This class manages the address book functionality.
 * Create Address Book.
 * Add contacts to Address book.
 * Delete Address Book.
 * Delete contacts from Address book.
 * Print all contacts.
 *  
 */

public class AddressBookService {

	private static final Logger LOGGER = Logger.getLogger(AddressBookService.class.getName());

	/**
	 * Name of the default address book type.
	 */

	public static final String HOME = "HOME";

	/**
	 * Address books store is a Map which holds all the address book data. Key
	 * is address book type. Values are contact details.
	 * 
	 */

	private Map<String, AddressBook> books;

	public AddressBookService() {
		books = new HashMap<String, AddressBook>();
		books.put(HOME, new AddressBook(HOME));
	}

	public Map<String, AddressBook> getBooks() {
		return books;
	}

	/**
	 * Create a new address book of specified type.
	 * 
	 * @param name
	 *            Name of the address book.
	 * @return newly created address book.
	 */
	public AddressBook setUpAddressBook(String type) {

		if (type.isEmpty())
			throw new RuntimeException("Address book type is required");

		AddressBook book = new AddressBook(type);
		getBooks().put(type, book);
		return book;
	}

	/**
	 * Remove existing address book
	 * 
	 * @param name
	 */
	public void removeAddressBook(String type) {
		getBooks().remove(type);
	}

	/**
	 * Add a new contact to the home (default) folder.
	 * 
	 * @param contact
	 *            contact name with valid name and phone.
	 * @return newly created contact.
	 */

	public Contact addContact(Contact contact) {
		if (contact == null)
			throw new ContactNotFoundException("A contact is required");
		if (contact.getName().isEmpty())
			throw new RuntimeException("Name is required");
		if (contact.getPhone().isEmpty())
			throw new RuntimeException("Phone is required");

		return addContact(contact, HOME);
	}

	/**
	 * Add a new contact to the specified folder
	 * 
	 * @param contact
	 * @param addressBook
	 * @return
	 */
	public Contact addContact(Contact contact, String addressBook) {
		if (contact == null)
			throw new ContactNotFoundException("A contact is required");
		if (contact.getName().isEmpty())
			throw new RuntimeException("Name is required");
		if (contact.getPhone().isEmpty())
			throw new RuntimeException("Phone is required");

		AddressBook book = createOrUpdateAddressBook(addressBook);
		contact.setBook(book);
		book.addContact(contact);

		return contact;

	}

	/**
	 * Create or update the address book as per specified address book.
	 * 
	 * @param addressBook
	 * @return
	 */

	public AddressBook createOrUpdateAddressBook(String addressBook) {
		AddressBook book = getAddressBook(addressBook);
		return book == null ? setUpAddressBook(addressBook) : book;
	}

	/**
	 * Retrieve the address book by name.
	 * 
	 * @param name
	 *            Name of the address book
	 * @return
	 */
	private AddressBook getAddressBook(String type) {
		return getBooks().get(type);
	}

	/**
	 * Remove contact by name from home folder.
	 * 
	 * @param name
	 *            Name of the contact
	 */
	public void removeContactByName(String name) {
		removeContactByNameAndAddressBook(name, HOME);
	}

	/**
	 * Remove contact by name and address book type.
	 * 
	 * @param name
	 *            Name of the contact
	 * @param addressBook
	 *            Name of the address book type
	 */

	public void removeContactByNameAndAddressBook(String name, String addressBook) {
		final AddressBook book = getAddressBook(addressBook);

		if (book == null)
			throw new AddressBookNotFoundException("Address book not found: " + addressBook);

		Contact contact = new Contact(name);
		book.removeContact(contact);
	}

	/**
	 * Retrieve all contacts by address book type.
	 * 
	 * @param addressBook
	 * @return
	 */

	public Set<Contact> getContacts(String addressBook) {
		AddressBook book = getAddressBook(addressBook);
		if (book == null)
			throw new AddressBookNotFoundException("Address book not found: " + addressBook);

		return book.getContacts();
	}
	
	/**
	 * Retrieve all contacts.
	 * @return
	 */
	public Set<Contact> getAllContacts() {
		Set<Contact> contacts = new HashSet<Contact>();
		Set<String> addressTypes = getBooks().keySet();
		addressTypes.forEach(addressType -> contacts.addAll(getContacts(addressType)));
		return contacts;
	}

	/**
	 * Display all contacts by type.
	 * 
	 * @param addressBook
	 */

	public void displayAllContacts(String addressBook) {
		LOGGER.info("List all contacts in" + addressBook);
		printAllContacts(getContacts(addressBook));
	}

	/**
	 * Display all contacts in address book
	 */
	public void displayAllContacts() {
		LOGGER.info("List all contacts in AddressBook");
		printAllContacts(getAllContacts());
	}

	private void printAllContacts(Set<Contact> contacts) {
		contacts.forEach(contact -> LOGGER.info(contact.getName() + "   " + contact.getPhone()));
	}

	/**
	 * Retrieve all the address books.
	 * 
	 * @return Retrieve all address books.
	 */
	public Set<String> getAllAdressBooks() {
		return getBooks().keySet();
	}

	public static void main(String args[]) {

	}

}
