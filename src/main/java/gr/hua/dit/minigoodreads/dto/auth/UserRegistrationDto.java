package gr.hua.dit.minigoodreads.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record UserRegistrationDto(
	@NotBlank
	@JsonProperty("username")
	String username,
	@NotBlank
	@JsonProperty("password")
	String password
) {
}
