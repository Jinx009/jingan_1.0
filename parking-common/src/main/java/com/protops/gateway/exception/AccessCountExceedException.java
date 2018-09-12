package com.protops.gateway.exception;

public class AccessCountExceedException extends Exception {

	private static final long serialVersionUID = -7478188376528990834L;

	public AccessCountExceedException() {
	}

	public AccessCountExceedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessCountExceedException(String message) {
		super(message);
	}

	public AccessCountExceedException(Throwable cause) {
		super(cause);
	}

}
