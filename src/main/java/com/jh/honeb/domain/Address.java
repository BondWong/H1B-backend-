package com.jh.honeb.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Address {
	@GraphId
	private Long ID;
	private String state;
	private String city;
	private String zipCode;
	private String street;

	public Address(String state, String city, String zipCode, String street) {
		this.state = state;
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
	}

	public Address() {

	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Address))
			return false;
		if (obj == this)
			return true;
		Address other = (Address) obj;
		return other.ID == this.ID;
	}

	public int hashCode() {
		if (this.ID == null)
			return super.hashCode();
		return this.ID.hashCode();
	}

}
