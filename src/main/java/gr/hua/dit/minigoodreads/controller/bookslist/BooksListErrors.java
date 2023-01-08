package gr.hua.dit.minigoodreads.controller.bookslist;

import gr.hua.dit.minigoodreads.exception.RestError;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public enum BooksListErrors implements RestError {
    LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "books_list.rename.notfound"),
    COULD_NOT_DELETE_LIST(HttpStatus.INTERNAL_SERVER_ERROR, "books_list.delete.failed");

    private final HttpStatus httpStatus;
    private final String messageCode;

    BooksListErrors(HttpStatus httpStatus, String messageCode) {
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
