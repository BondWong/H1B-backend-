package com.jh.honeb.web.helper;

public class NameTuple {
	private String companyName;
	private String titleName;

	public NameTuple(String companyName, String titleName) {
		this.companyName = companyName;
		this.titleName = titleName;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof NameTuple))
			return false;
		NameTuple other = (NameTuple) obj;
		if (!other.companyName.equals(this.companyName) || !other.titleName.equals(this.titleName))
			return false;
		return true;
	}

	public int hashCode() {
		return this.companyName.hashCode() + this.titleName.hashCode() * 33;
	}
}
