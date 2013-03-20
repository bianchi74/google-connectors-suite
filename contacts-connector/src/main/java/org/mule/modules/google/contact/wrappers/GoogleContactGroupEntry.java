/**
 *
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.modules.google.contact.wrappers;

import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.SystemGroup;

public class GoogleContactGroupEntry extends GoogleContactBaseEntity<ContactGroupEntry> {
	
	private ContactGroupEntry wrapped;
	
	public GoogleContactGroupEntry() {
		super(new ContactGroupEntry());
	}
	
	public GoogleContactGroupEntry(ContactGroupEntry contactEntry) {
		super(contactEntry != null ? contactEntry : new ContactGroupEntry());
	}
	
	public void setSystemGroup(SystemGroup systemGroup) {
		wrapped.setSystemGroup(systemGroup);
	}
	
	public SystemGroup getSystemGroup() {
		return wrapped.getSystemGroup();
	}
	
	// Getter only
	public String getPlainTextContent() {
		return wrapped.getPlainTextContent();
	}
	
}
