package com.jh.honeb.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Company {
	@GraphId
	private Long ID;
	private String name;
	@Relationship(type = "at")
	private Address address;
	private String contact;
	@Relationship(type = "has")
	private List<Position> positions;

	public Company() {
	}

	public Company(String name, String contact, String state, String city, String zipCode, String street) {
		this.name = name;
		this.contact = contact;
		this.address = new Address(state, city, zipCode, street);
		this.positions = new ArrayList<Position>();
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

	public void setAddress(Address address) {
		this.address = address;
	}

	public void addPosition(Position positoin) {
		if (this.positions == null)
			this.positions = new ArrayList<Position>();
		this.positions.add(positoin);
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
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
