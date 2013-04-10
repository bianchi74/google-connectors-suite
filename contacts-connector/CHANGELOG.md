1.7.4
=====
	* Removed public getWrapped() from GoogleContactBaseEntry and added a static method to get the wrapped entity. This is to prevent the mapping to Ex. object-to-json of the wrapped entity.

1.7.3
=====
	* Added getters/setters to GoogleContactEntry POJO
	* Changed getEmailAddresses and setEmailAddresses signatures. Previously using List<String>, now List<Email>

1.7.2
=====
	* Changed UpdateDateTime getters/setters to Update

1.7.1
=====
	* Minor fix in wrappers (Fix CLDCONNECT-822)

1.7
===
	* Added demo project (Mule Studio Application)
1.6
===
	* WARNING: the operation signatures of the connector has been changed. This may lead that a Mule Studio project maded with a previous version of the connector stop working.
	* Wrapped entity objects for simplification for all the Operations
1.0
===
	* Initial release