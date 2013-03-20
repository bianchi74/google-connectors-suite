/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.wrappers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;

public class GoogleContactEntry extends GoogleContactBaseEntity<ContactEntry> {
	
	private ContactEntry wrapped;
	
	public GoogleContactEntry() {
		super(new ContactEntry());
	}
	
	public GoogleContactEntry(ContactEntry contactEntry) {
		super(contactEntry != null ? contactEntry : new ContactEntry());
	}
	
	public void setFamilyName(String familyName) {
		Name name = getWrappedName();
		
		FamilyName fName = name.getFamilyName();
		if (fName == null) {
			fName = new FamilyName();
			fName.setImmutable(false);
		}
		
		fName.setValue(familyName);
	}
	
	public String getFamilyName() {
		return wrapped.getName() != null && wrapped.getName().getFamilyName() != null ? wrapped.getName().getFamilyName().getValue() : null;
	}
	
	public void setGivenName(String givenName) {
		Name name = getWrappedName();
		
		GivenName gName = name.getGivenName();
		if (gName == null) {
			gName = new GivenName();
			gName.setImmutable(false);
		}
		
		gName.setValue(givenName);
	}
	
	public String getGivenName() {
		return wrapped.getName() != null && wrapped.getName().getGivenName() != null ? wrapped.getName().getGivenName().getValue() : null;
	}
	
	public void setFullName(String fullName) {
		Name name = getWrappedName();
		
		FullName fName = name.getFullName();
		if (fName == null) {
			fName = new FullName();
			fName.setImmutable(false);
		}
		
		fName.setValue(fullName);
	}
		
	// Only getter
	public String getContactPhotoLink() {
		return wrapped.getContactPhotoLink() != null ? wrapped.getContactPhotoLink().getHref() : null;
	}
	
	public void setEmailAddresses(List<String> emailAddresses) {
		List<Email> emails = new LinkedList<Email>();
		
		if (emailAddresses != null) {
			for (String email : emailAddresses) {
				Email e = new Email();
				e.setImmutable(false);
				e.setAddress(email);
				emails.add(e);
			}
		} 
		
		wrapped.setEmailAddresses(emails);
	}
	
	public List<String> getEmailAddresses() {
		List<String> emails = null;
		
		if (wrapped.getEmailAddresses() != null) {
			emails = new LinkedList<String>();
			
			Iterator<Email> eIt = wrapped.getEmailAddresses().iterator();
			while (eIt.hasNext()) {
				Email e = eIt.next();
				emails.add(e.getAddress());
			}
		}
		
		return emails;
	}
		
	/**
	 * Because three different properties use the name inside the wrapper entity and if it does not exist it creates a new one, we need to prevent
	 * concurrent problems isolating the retrieve/create in a synch method 
	 * 
	 * @return the existent/new Name assigned to the wrapped
	 */
	synchronized private Name getWrappedName() {
		Name name = wrapped.getName();
		if (name == null) {
			name = new Name();
			wrapped.setName(name);
		}
		
		return name;
	}
}
