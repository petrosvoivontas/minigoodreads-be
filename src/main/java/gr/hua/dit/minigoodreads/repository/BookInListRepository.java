package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.BookInList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BookInListRepository extends JpaRepository<BookInList, String> {

    Set<BookInList> findByUidAndBooksListResourceId(String uid, String listResourceId);

    @Transactional
    long deleteByUidAndBooksListResourceIdAndBookId(String uid, String listResourceId, String bookId);
}
