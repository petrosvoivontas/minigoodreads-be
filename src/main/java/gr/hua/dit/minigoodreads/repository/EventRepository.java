package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
