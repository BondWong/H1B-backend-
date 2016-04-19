package com.jh.honeb.domain.factory;

import java.util.Map;

import com.jh.honeb.domain.Position;

public class PositionFactory implements Factory<Position> {
	private static PositionFactory instance;

	private PositionFactory() {

	}

	public static PositionFactory newInstance() {
		if (instance == null) {
			synchronized (PositionFactory.class) {
				if (instance == null) {
					instance = new PositionFactory();
				}
			}
		}

		return instance;
	}

	public Position create(Map<String, String> data) {
		// TODO Auto-generated method stub
		Position position = new Position(data.get("title"), data.get("salary"), data.get("visaType"),
				data.get("workState"), data.get("workCity"), data.get("workZipCode"));
		return position;
	}

}
