package gr.hua.dit.minigoodreads.controller.auth;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.auth.UserDto;
import gr.hua.dit.minigoodreads.dto.auth.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

	private final JdbcUserDetailsManager jdbcUserDetailsManager;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder) {
		this.jdbcUserDetailsManager = jdbcUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
	}

	private UserDto mapToUserDto(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		return new UserDto(userDetails.getUsername(), roles, userDetails.isEnabled());
	}

	@PostMapping("/register")
	ResponseEntity<ResponseWrapper<Void>> register(@RequestBody UserRegistrationDto userRegistration) {
		// check if user already exists
		if (jdbcUserDetailsManager.userExists(userRegistration.username())) {
			throw handleError(AuthErrors.USERNAME_EXISTS);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Roles.USER.getAuthority()));
		User user = new User(
			userRegistration.username(),
			passwordEncoder.encode(userRegistration.password()),
			authorities
		);
		jdbcUserDetailsManager.createUser(user);
		return ResponseEntity.ok().build();
	}

	@Secured("ROLE_ADMIN")
	@PatchMapping("/{username}")
	ResponseEntity<ResponseWrapper<UserDto>> changeEnabledStatus(@PathVariable("username") String username) {
		// retrieve userDetails for the given username
		UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername(username);

		// clone userDetails with the opposite status
		UserDetails updatedUser = User.withUserDetails(userDetails)
			.disabled(userDetails.isEnabled())
			.build();

		// store the updated user details
		jdbcUserDetailsManager.updateUser(updatedUser);
		return ResponseEntity.ok(new ResponseWrapper<>(mapToUserDto(updatedUser)));
	}
}
