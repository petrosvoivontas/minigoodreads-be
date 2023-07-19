package gr.hua.dit.minigoodreads.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserLoginDto(
	@JsonProperty("username")
	String username,
	@JsonProperty("password")
	String password
){
}
