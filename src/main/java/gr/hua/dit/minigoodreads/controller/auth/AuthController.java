package gr.hua.dit.minigoodreads.controller.auth;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.auth.UserDto;
import gr.hua.dit.minigoodreads.dto.auth.UserRegistrationDto;
import gr.hua.dit.minigoodreads.repository.UsersRepository;
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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthController extends BaseController {

	private final JdbcUserDetailsManager jdbcUserDetailsManager;
	private final PasswordEncoder passwordEncoder;
	private final UsersRepository usersRepository;

	@Autowired
	public AuthController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
		this.jdbcUserDetailsManager = jdbcUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
		this.usersRepository = usersRepository;
	}

	private UserDto mapToUserDto(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		return new UserDto(userDetails.getUsername(), roles, userDetails.isEnabled());
	}

	private UserDto mapUserToUserDto(gr.hua.dit.minigoodreads.entity.User user) {
		return new UserDto(user.getUsername(), Collections.emptyList(), user.isEnabled());
	}

	@PostMapping("/register")
	ResponseEntity<ResponseWrapper<Void>> register(@Valid @RequestBody UserRegistrationDto userRegistration) {
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
	@PostMapping("/admin")
	ResponseEntity<ResponseWrapper<Void>> addAdmin(@Valid @RequestBody UserRegistrationDto userRegistration) {
		// check if user already exists
		if (jdbcUserDetailsManager.userExists(userRegistration.username())) {
			throw handleError(AuthErrors.USERNAME_EXISTS);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(Roles.ADMIN.getAuthority()));
		User user = new User(
			userRegistration.username(),
			passwordEncoder.encode(userRegistration.password()),
			authorities
		);
		jdbcUserDetailsManager.createUser(user);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/login")
	ResponseEntity<ResponseWrapper<UserDto>> login(Principal principal) {
		UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername(principal.getName());
		return ResponseEntity.ok(new ResponseWrapper<>(mapToUserDto(userDetails)));
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/users")
	ResponseEntity<ResponseWrapper<List<UserDto>>> getAllUsers() {
		List<gr.hua.dit.minigoodreads.entity.User> users = usersRepository.getAllRegularUsers();
		List<UserDto> responseBody = users.stream().map(this::mapUserToUserDto).toList();
		return ResponseEntity.ok(new ResponseWrapper<>(responseBody));
	}

	@Secured("ROLE_ADMIN")
	@PatchMapping("/{username}")
	ResponseEntity<ResponseWrapper<UserDto>> changeEnabledStatus(@NotEmpty @PathVariable("username") String username) {
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
