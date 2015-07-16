package com.fravokados.mindim.util;

/**
 * @author Nuklearwurst
 */
public class SimpleObjectReference<T> {

	private T value;

	public SimpleObjectReference() {
		value = null;
	}

	public SimpleObjectReference(T object) {
		this.value = object;
	}

	public T get() {
		return value;
	}

	public void set(T value) {
		this.value = value;
	}

	public boolean isNull() {
		return value == null;
	}
}
