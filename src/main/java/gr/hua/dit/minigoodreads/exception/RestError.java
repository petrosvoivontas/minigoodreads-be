package gr.hua.dit.minigoodreads.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

public interface RestError {

    @NotNull
    String getCode();

    @NotNull
    HttpStatus getHttpStatus();

    @NotNull
    String getMessageCode();
}
