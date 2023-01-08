package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.BooksList;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BooksListRepository extends JpaRepository<BooksList, String> {

    Set<BooksList> findBooksListsByUidEqualsOrderByListIdAsc(String uid);

    @Nullable
    BooksList findFirstByUidEqualsOrderByListIdDesc(String uid);

    @Nullable
    BooksList findFirstByUidAndListId(String uid, int listId);

    @Transactional
    long deleteByListIdAndUid(int listId, String uid);
}
