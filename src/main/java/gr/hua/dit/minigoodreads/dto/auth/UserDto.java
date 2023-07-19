package gr.hua.dit.minigoodreads.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserDto(
	@JsonProperty("username")
	String username,
	@JsonProperty("roles")
	List<String> roles,
	@JsonProperty("enabled")
	boolean isEnabled
) {
}
