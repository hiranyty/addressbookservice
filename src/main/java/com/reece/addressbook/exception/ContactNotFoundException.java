package com.reece.addressbook.exception;

public class ContactNotFoundException extends RuntimeException {

	public ContactNotFoundException(String s) {
		super(s);
	}

}
