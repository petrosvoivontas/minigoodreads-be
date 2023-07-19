package gr.hua.dit.minigoodreads.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRegistrationDto(
	@JsonProperty("username")
	String username,
	@JsonProperty("password")
	String password
) {
}
