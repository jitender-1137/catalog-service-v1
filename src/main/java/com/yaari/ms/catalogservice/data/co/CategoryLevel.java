package com.yaari.ms.catalogservice.data.co;

import java.util.HashSet;
import java.util.Set;

public enum CategoryLevel {
	L1, L2, L3;

	private static Set<String> values = new HashSet<>();

	static {
		for (CategoryLevel choice : CategoryLevel.values()) {
			values.add(choice.name());
		}
	}

	public static boolean contains(String value) {
		return values.contains(value);
	}

	public static String l1Name() {
		return L1.name();
	}

	public static String l2Name() {
		return L2.name();
	}

	public static String l3Name() {
		return L3.name();
	}
}
