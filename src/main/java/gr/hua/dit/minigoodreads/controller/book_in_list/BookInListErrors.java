package gr.hua.dit.minigoodreads.controller.book_in_list;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public enum BookInListErrors implements RestError {
    LIST_NOT_FOUND(HttpStatus.BAD_REQUEST, "book_in_list.list.notfound");

    private final HttpStatus httpStatus;
    private final String messageCode;

    BookInListErrors(HttpStatus httpStatus, String messageCode) {
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
