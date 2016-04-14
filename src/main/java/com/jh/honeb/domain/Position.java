package com.jh.honeb.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Position {
	@GraphId
	private Long ID;
	@Index
	private String name;
	private String salary;
	private String visaType;
	@Relationship(type = "at")
	private Address address;
	@Relationship(type = "of")
	private Company company;

	public Position() {

	}

	public Position(String name, String salary, String visaType, String state, String city, String zipCode) {
		this.name = name;
		this.salary = salary;
		this.visaType = visaType;
		Address address = new Address(state, city, zipCode, "");
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getSalary() {
		return salary;
	}

	public String getVisaType() {
		return visaType;
	}

	public Address getLocation() {
		return address;
	}

	public void setLocation(Address address) {
		this.address = address;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Position))
			return false;
		if (obj == this)
			return true;
		Position other = (Position) obj;
		return this.ID == other.ID;
	}

	public int hashCode() {
		if (this.ID == null)
			return super.hashCode();
		return this.ID.hashCode();
	}

}
