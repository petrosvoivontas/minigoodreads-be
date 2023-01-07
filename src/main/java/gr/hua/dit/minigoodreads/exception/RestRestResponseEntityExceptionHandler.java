package gr.hua.dit.minigoodreads.exception;

import org.jetbrains.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class RestRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_ARG_ERROR_CODE = "INVALID_ARG";

    private static final String DEFAULT_INVALID_ARG_MESSAGE_CODE = "invalidarg.error.default";
    private static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred";

    private final MessageSource messageSource;

    private String defaultInvalidArgErrorMessage;

    public RestRestResponseEntityExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String getDefaultInvalidArgErrorMessage(String fieldName) {
        if (defaultInvalidArgErrorMessage != null) {
            return defaultInvalidArgErrorMessage;
        }
        defaultInvalidArgErrorMessage = messageSource.getMessage(DEFAULT_INVALID_ARG_MESSAGE_CODE, new String[]{fieldName}, Locale.getDefault());
        return defaultInvalidArgErrorMessage;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(fieldError -> {
                String message = fieldError.getDefaultMessage();
                if (message == null) {
                    message = getDefaultInvalidArgErrorMessage(fieldError.getField());
                }
                return message;
            }).get();
        ErrorResponseBody responseBody = new ErrorResponseBody(INVALID_ARG_ERROR_CODE, errorMessage);
        return createResponseEntity(responseBody, headers, HttpStatus.BAD_REQUEST, request);
    }

    private String getMessageFromCode(@Nullable String messageCode) {
        if (messageCode == null) {
            return GENERIC_ERROR_MESSAGE;
        }
        return messageSource.getMessage(messageCode, null, GENERIC_ERROR_MESSAGE, Locale.getDefault());
    }

    @ExceptionHandler({RestErrorException.class})
    public ResponseEntity<ErrorResponseBody> handleRestErrorException(RestErrorException e, WebRequest request) {
        ErrorResponseBody body = new ErrorResponseBody(e.getMessage(), getMessageFromCode(e.getMessageCode()));
        return new ResponseEntity<>(body, e.getHttpStatus());
    }
}
