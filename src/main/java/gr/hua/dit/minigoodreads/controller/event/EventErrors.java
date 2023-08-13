package gr.hua.dit.minigoodreads.controller.event;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public enum EventErrors implements RestError {
    INVALID_EVENT_NAME(HttpStatus.BAD_REQUEST, "event.name.invalid"),
    INVALID_EVENT_PARAMS(HttpStatus.BAD_REQUEST, "event.params.invalid");

    private final HttpStatus httpStatus;
    private final String messageCode;

    EventErrors(HttpStatus httpStatus, String messageCode) {
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
