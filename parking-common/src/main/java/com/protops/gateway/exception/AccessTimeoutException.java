package com.protops.gateway.exception;

public class AccessTimeoutException extends Exception {

	private static final long serialVersionUID = -7478188376528990834L;

	public AccessTimeoutException() {
	}

	public AccessTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTimeoutException(String message) {
		super(message);
	}

	public AccessTimeoutException(Throwable cause) {
		super(cause);
	}

}
