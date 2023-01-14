package gr.hua.dit.minigoodreads.controller.reading_progress;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public enum ReadingProgressErrors implements RestError {
    NOT_FOUND(HttpStatus.NOT_FOUND, "reading_progress.notfound"),
    BOOK_NOT_FOUND_IN_LIST(HttpStatus.BAD_REQUEST, "book_in_list.book.notfound");

    private final HttpStatus httpStatus;
    private final String messageCode;

    ReadingProgressErrors(HttpStatus httpStatus, String messageCode) {
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
