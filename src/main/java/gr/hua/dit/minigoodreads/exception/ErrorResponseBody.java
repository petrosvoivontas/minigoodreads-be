package gr.hua.dit.minigoodreads.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseBody(
    @JsonProperty("code")
    String code,
    @JsonProperty("errorMessage")
    String errorMessage
) {
}
