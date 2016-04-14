package com.jh.honeb.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Company {
	@GraphId
	private Long ID;
	@Index
	private String name;
	@Relationship(type = "at")
	private Address address;
	private String contact;

	public Company() {
	}

	public Company(String name, String contact, String state, String city, String zipCode, String street) {
		this.name = name;
		this.contact = contact;
		this.address = new Address(state, city, zipCode, street);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public Address getAddress() {
		return address;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Company))
			return false;
		if (obj == this)
			return true;
		Company other = (Company) obj;
		return other.ID == this.ID;
	}

	public int hashCode() {
		if (this.ID == null)
			return super.hashCode();
		return this.ID.hashCode();
	}

}
