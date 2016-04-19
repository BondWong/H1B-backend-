package com.jh.honeb.domain.factory;

import java.util.Map;

public interface Factory<T> {
	public T create(Map<String, String> data);
}
