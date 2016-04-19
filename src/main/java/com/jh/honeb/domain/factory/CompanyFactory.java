package com.jh.honeb.domain.factory;

import java.util.Map;

import com.jh.honeb.domain.Company;

public class CompanyFactory implements Factory<Company> {
	private static CompanyFactory instance;

	private CompanyFactory() {
	}

	public static CompanyFactory newInstance() {
		if (instance == null) {
			synchronized (CompanyFactory.class) {
				if (instance == null) {
					instance = new CompanyFactory();
				}
			}
		}
		return instance;
	}

	public Company create(Map<String, String> data) {
		// TODO Auto-generated method stub
		Company company = new Company(data.get("name"), data.get("contact"), data.get("state"), data.get("city"),
				data.get("zipCode"), data.get("street"));
		return company;
	}

}
