package com.reece.addressbook;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.reece.addressbook.exception.AddressBookNotFoundException;
import com.reece.addressbook.exception.ContactNotFoundException;
import com.reece.addressbook.model.Contact;
import com.reece.addressbook.service.AddressBookService;

public class Junit4AddressBookServiceTest {

	private AddressBookService bookService;

	public static final String HOME = "HOME";
	public static final String WORK = "WORK";
	public static final String FAMILY = "FAMILY";
	public static final String FRIENDS = "FRIENDS";

	/**
	 * Setup initial records before each step.
	 * 
	 * @throws Exception
	 */
	@Before
	public void before() throws Exception {

		/**
		 * Add initial contacts to the home address book
		 */

		bookService = new AddressBookService();

		bookService.addContact(new Contact("Harry", "04 22179380"));
		bookService.addContact(new Contact("Jack", "04 22189900"));
		bookService.addContact(new Contact("Lisa", "04 2299888"));

		/**
		 * Add initial contacts to work address book
		 */

		bookService.setUpAddressBook(WORK);

		bookService.addContact(new Contact("Sava", "04 25664445"), WORK);
		bookService.addContact(new Contact("Chris", "04 35665775"), WORK);
		bookService.addContact(new Contact("Lisa", "04 4555678"), WORK);
		bookService.addContact(new Contact("Binca", "04 25663335"), WORK);
		bookService.addContact(new Contact("Piper", "04 35665775"), WORK);
		bookService.addContact(new Contact("Sammi", "04 4555678"), WORK);

	}

	/**
	 * Validate the address book count.
	 */

	@Test
	public void Validate_AddressBooks_Count() {
		assertEquals(2, bookService.getAllAdressBooks().size());
	}

	/**
	 * Validate the number of contacts in home address book.
	 */
	@Test
	public void Validate_Contacts_Count_By_Home_AddressBook() {
		assertEquals(3, bookService.getContacts(HOME).size());
	}

	/**
	 * Validate the number of contacts in work address book.
	 */

	@Test
	public void Validate_Contacts_Count_By_Work_AddressBook() {
		assertEquals(6, bookService.getContacts(WORK).size());
	}

	/**
	 * Validate the number of contacts in address book.
	 */
	@Test
	public void Validate_All_Contacts_Count() {
		assertEquals(8, bookService.getAllContacts().size());
	}

	/**
	 * Test ContactNotFoundException.
	 */

	@Test(expected = ContactNotFoundException.class)
	public void should_Throw_ContactNotFoundException_For_ContactIsNull_In_AddContact_To_HomeAddressBook() {
		bookService.addContact(null);
	}

	/**
	 * Test for ContactNotFoundException.
	 */
	@Test(expected = ContactNotFoundException.class)
	public void Should_Not_Throw_ContactNotFoundException_For_ContactIsNotNull_In_AddContact_To_HomeAddressBook() {
		bookService.addContact(new Contact("Osaka", "098745634"));
	}

	/**
	 * Test for AddressBookNotFoundException when Address Type is null.
	 */
	@Test(expected = AddressBookNotFoundException.class)
	public void Should_Throw_AddressBookNotFoundException_For_AddressTypeIsNull_In_RemoveContactByNameAndAddressBook() {
		bookService.removeContactByNameAndAddressBook("Osaka", null);
	}

	/**
	 * Test for AddressBookNotFoundException when Address Type is null.
	 */
	@Test(expected = AddressBookNotFoundException.class)
	public void Should_Throw_AddressBookNotFoundException_For_AddressTypeIsNull_In_getContacts() {
		bookService.getContacts(null);
	}

	/**
	 * Validate add contact method for home address book.
	 */
	@Test
	public void Add_Valid_Contact_To_Home_AddressBook() {
		bookService.addContact(new Contact("Callum", "078908768"));

		Set<Contact> contacts = bookService.getContacts(HOME);

		assertEquals(4, contacts.size());
		assertTrue(contacts.contains(new Contact("Callum")));

		bookService.displayAllContacts(HOME);
	}

	/**
	 * Validate add contact method for work address book.
	 */
	@Test
	public void Add_Valid_Contact_To_Work_AddressBook() {
		bookService.addContact(new Contact("Don", "078555999"), WORK);

		Set<Contact> contacts = bookService.getContacts(WORK);

		assertEquals(7, contacts.size());
		assertTrue(contacts.contains(new Contact("Don")));
	}

	/**
	 * Validate Name is required.
	 */
	@Test
	public void Should_Throw_RunTimeException_For_Contact_Name_IsEmpty_In_AddContact_To_HomeAddressBook() {

		try {
			bookService.addContact(new Contact("", "098745634"));
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().equals("Name is required"));

		}
		fail("Contatct name cannot be empty");
	}

	/**
	 * Validate Phone is required.
	 */
	@Test
	public void Should_Throw_RunTimeException_For_Contact_Phone_IsEmpty_In_AddContact_To_HomeAddressBook() {

		try {
			bookService.addContact(new Contact("Soloman", "098745734"));
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().equals("Phone is required"));

		}
		fail("Contact phone number cannot be empty");
	}

	@Test
	public void Should_Throw_RunTimeException_For_AddressBookType_IsEmpty_In_SetUpAddressBook() {

		try {
			bookService.setUpAddressBook("");
		} catch (RuntimeException ex) {
			assertTrue(ex.getMessage().equals("Address book type is required"));

		}
		fail("Address book type cannot be empty");
	}

	/**
	 * Validate contact remove by name method.
	 */
	@Test
	public void Remove_ContactByName_from_HomeAddressBook() {
		bookService.removeContactByName("Harry");
		Set<Contact> contacts = bookService.getContacts(HOME);

		assertEquals(2, contacts.size());
		assertFalse(contacts.contains(new Contact("Harry")));
	}

	/**
	 * Validate contact removed by name and address book type.
	 */
	@Test
	public void Remove_ContactByName_And_Addressbooktype() {
		bookService.removeContactByNameAndAddressBook("Lisa", WORK);
		Set<Contact> contacts = bookService.getContacts(WORK);

		assertEquals(5, contacts.size());
		assertFalse(contacts.contains(new Contact("Lisa")));
	}

	@Test
	public void Add_Multipe_Contacts_To_Addressbooks() {
		bookService.addContact(new Contact("Farther", "0094 037890567"), FAMILY);
		bookService.addContact(new Contact("Mother", "0094 0379086785"), FAMILY);
		bookService.addContact(new Contact("brother", "0065 0111086785"), FAMILY);
		bookService.addContact(new Contact("Uncle", "0044 0122086785"), FAMILY);

		bookService.addContact(new Contact("Ronan", "0425 237890567"), FRIENDS);
		bookService.addContact(new Contact("David", "0422 1379086785"), FRIENDS);
		bookService.addContact(new Contact("Ben", "00435 3111086785"), FRIENDS);

		Set<String> addressBooks = bookService.getAllAdressBooks();

		assertEquals(4, bookService.getAllAdressBooks().size());
		assertEquals(3, bookService.getContacts(FRIENDS).size());
		assertEquals(4, bookService.getContacts(FAMILY).size());

		assertTrue(addressBooks.contains(FAMILY));
		assertTrue(addressBooks.contains(FRIENDS));

	}

	/**
	 * Validate unique contacts in multiple address books
	 */

	@Test
	public void Add_Unique_Contacts_To_Muliple_Addressbooks() {
		// Contact harry is the unique record.

		bookService.addContact(new Contact("Uncle", "0044 0122086785"), FAMILY);

		bookService.addContact(new Contact("Harry", "04 22179380"), FAMILY);

		bookService.addContact(new Contact("David", "0422 1379086785"), FRIENDS);
		bookService.addContact(new Contact("Ben", "00435 3111086785"), FRIENDS);
		bookService.addContact(new Contact("Harry", "04 22179380"), FRIENDS);

		bookService.addContact(new Contact("Harry", "04 22179380"), WORK);

		assertEquals(4, bookService.getAllAdressBooks().size());
		assertEquals(3, bookService.getContacts(FRIENDS).size());
		assertEquals(2, bookService.getContacts(FAMILY).size());
		assertEquals(7, bookService.getContacts(WORK).size());
		assertEquals(11, bookService.getAllContacts().size());

		assertTrue(bookService.getContacts(FRIENDS).contains(new Contact("Harry")));
		assertTrue(bookService.getContacts(FAMILY).contains(new Contact("Harry")));
		assertTrue(bookService.getContacts(WORK).contains(new Contact("Harry")));

	}

	/**
	 * Validate remove address book type
	 */
	@Test
	public void Remove_Addressbooktype() {
		bookService.removeAddressBook(WORK);
		Set<String> addressBooks = bookService.getAllAdressBooks();

		assertEquals(1, addressBooks.size());
		assertFalse(addressBooks.contains(WORK));
	}

	/**
	 * Print all the contacts.
	 */

	@Test
	public void DisplayAllcontacts_Test() {
		bookService.displayAllContacts();
	}

	/**
	 * Print all the contacts in Home address book.
	 */

	@Test
	public void DisplayHomeContacts_Test() {
		bookService.displayAllContacts(HOME);
	}

	@Test
	public void DisplayWorkContacts_Test() {
		bookService.displayAllContacts(WORK);
	}

	@Test
	public void justAnExample() {
		System.out.println("This test method should be run");
	}

	@After
	public void teardown() throws Exception {
		bookService = null;
	}

}
