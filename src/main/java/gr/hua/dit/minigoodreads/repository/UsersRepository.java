package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, String> {

	@Query(
		value = "select a.username, u.enabled, group_concat(a.authority separator ',') roles " +
			"from authorities a join users u on u.username = a.username " +
			"group by a.username, u.enabled " +
			"having roles not like '%ROLE_ADMIN%'",
		nativeQuery = true
	)
	List<User> getAllRegularUsers();
}
