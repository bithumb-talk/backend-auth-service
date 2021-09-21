package com.bithumb.auth.common.response;

public enum SuccessCode {

	USER_REGISTER_SUCCESS("REGISTER SUCCESS"),
	USER_SIGN_UP_SUCCESS("SIGN UP SUCCESS"),
	USER_LOGIN_SUCCESS("LOGIN SUCCESS"),
	USER_LOGOUT_SUCCESS("LOGOUT SUCCESS"),
	USER_FINDALL_SUCCESS("FIND ALL MEMBER Success"),
	USER_FINDMEMBER_SUCCESS("Find MEMBER SUCCESS"),
	USER_UPDATE_SUCCESS("UPDATE SUCCESS"),
	USER_DELETE_SUCCESS("DELETE SUCCESS");

	private final String message;

	SuccessCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
