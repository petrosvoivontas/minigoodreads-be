package gr.hua.dit.minigoodreads.controller.auth;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public enum AuthErrors implements RestError {
	USERNAME_EXISTS(HttpStatus.BAD_REQUEST, "auth.register.user_exists");

	private final HttpStatus httpStatus;
	private final String messageCode;

	AuthErrors(HttpStatus httpStatus, String messageCode) {
		this.httpStatus = httpStatus;
		this.messageCode = messageCode;
	}

	@Override
	public @NotNull String getCode() {
		return name();
	}

	@Override
	public @NotNull HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public @NotNull String getMessageCode() {
		return messageCode;
	}
}
