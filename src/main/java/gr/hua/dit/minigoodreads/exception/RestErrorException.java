package gr.hua.dit.minigoodreads.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

public class RestErrorException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String messageCode;

    public RestErrorException(String message, HttpStatus httpStatus, String messageCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.messageCode = messageCode;
    }

    @NotNull
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Nullable
    public String getMessageCode() {
        return messageCode;
    }
}
