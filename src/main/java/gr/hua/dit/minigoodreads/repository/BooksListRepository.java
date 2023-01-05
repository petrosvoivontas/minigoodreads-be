package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.BooksList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksListRepository extends JpaRepository<BooksList, String> {

    List<BooksList> findBooksListsByUidEqualsOrderByListIdAsc(String uid);

    BooksList findFirstByUidEqualsOrderByListIdDesc(String uid);
}
