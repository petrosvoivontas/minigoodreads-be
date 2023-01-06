package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.BooksList;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksListRepository extends JpaRepository<BooksList, String> {

    List<BooksList> findBooksListsByUidEqualsOrderByListIdAsc(String uid);

    @Nullable
    BooksList findFirstByUidEqualsOrderByListIdDesc(String uid);

    @Nullable
    BooksList findFirstByUidAndListId(String uid, int listId);
}
