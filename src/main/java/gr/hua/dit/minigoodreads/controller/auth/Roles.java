package gr.hua.dit.minigoodreads.controller.auth;

public enum Roles {
	USER("USER"),
	ADMIN("ADMIN");

	private final String role;

	Roles(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public String getAuthority() {
		return "ROLE_" + role;
	}
}
