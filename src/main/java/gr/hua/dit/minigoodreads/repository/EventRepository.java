package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EventRepository extends JpaRepository<Event, String> {

	Set<Event> findByUid(String uid);
}
