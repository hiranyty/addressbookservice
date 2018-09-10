package com.reece.addressbook.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AddressBook implements Serializable {

	private static final long serialVersionUID = 2246265929117802678L;

	private String type;
	private Set<Contact> contacts;

	public AddressBook(String type) {
		this.setType(type);

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Contact> getContacts() {
		if (contacts == null) {
			contacts = new HashSet<Contact>();
		}
		return contacts;
	}

	public void addContact(Contact contact) {
		getContacts().add(contact);
	}

	public void removeContact(Contact contact) {
		getContacts().remove(contact);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressBook other = (AddressBook) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AddressBook [type=" + type + ", contacts=" + contacts + "]";
	}
}
