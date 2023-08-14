package gr.hua.dit.minigoodreads.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "username", length = 50, nullable = false)
	private String username;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
