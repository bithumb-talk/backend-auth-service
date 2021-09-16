package com.bithumb.auth.common.response;

public enum ErrorCode {

	PASSWORD_NOT_MATCH("Password Not Match"),
	ID_NOT_EXIST("ID NOT EXIST"),
	ID_EXIST("ID ALREADY EXIST"),
	ID_NOT_MATCH("ID NOT MATCH"),
	SESSION_NO_AUTHORIZED("SESSION NO AUTH"),
	AUTH_ANNOTATION_REQUIRED("INTERCEPTOR NO ANNOTATION"),
	AUTH_ROLE_NOT_MATCH("AUTH ROLE NOT MATCH"),
	ENTITY_INFO_NOT_EXIST("ENTITY_INFO_NOT_EXIST");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}