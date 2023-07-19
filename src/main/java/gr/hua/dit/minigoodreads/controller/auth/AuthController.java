package gr.hua.dit.minigoodreads.controller.auth;

import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.auth.UserLoginDto;
import gr.hua.dit.minigoodreads.dto.auth.UserLoginResultDto;
import gr.hua.dit.minigoodreads.dto.auth.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	private final JdbcUserDetailsManager jdbcUserDetailsManager;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthController(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.jdbcUserDetailsManager = jdbcUserDetailsManager;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/register")
	ResponseEntity<ResponseWrapper<Void>> register(@RequestBody UserRegistrationDto userRegistration) {
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

	@PostMapping("/login")
	ResponseEntity<ResponseWrapper<UserLoginResultDto>> login(@RequestBody UserLoginDto userLogin) {
		// authenticate user
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				userLogin.username(),
				userLogin.password()
			)
		);
		List<String> roles = authentication.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.toList();
		// get user details
		User userDetails = (User) authentication.getPrincipal();
		UserLoginResultDto response = new UserLoginResultDto(
			userDetails.getUsername(),
			roles,
			userDetails.isEnabled()
		);
		return ResponseEntity.ok(new ResponseWrapper<>(response));
	}
}
