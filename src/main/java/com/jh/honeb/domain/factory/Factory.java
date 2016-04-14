package com.jh.honeb.domain.factory;

import java.util.Map;

import com.jh.honeb.domain.Company;
import com.jh.honeb.domain.Position;

public class Factory {
	private static Factory instance;

	private Factory() {
	}

	public static Factory newInstance() {
		if (instance == null) {
			synchronized (Factory.class) {
				if (instance == null) {
					instance = new Factory();
				}
			}
		}

		return instance;
	}

	public Position create(Map<String, String> data) {
		Company company = new Company(data.get("name"), data.get("contact"), data.get("state"), data.get("city"),
				data.get("zipCode"), data.get("street"));
		Position position = new Position(data.get("title"), data.get("salary"), data.get("visaType"),
				data.get("workState"), data.get("workCity"), data.get("workZipCode"));
		position.setCompany(company);
		return position;
	}

}
