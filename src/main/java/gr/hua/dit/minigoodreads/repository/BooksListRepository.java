package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.BooksList;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface BooksListRepository extends JpaRepository<BooksList, String> {

    Set<BooksList> findByUidEqualsOrUidNullOrderByListIdAsc(String uid);

    @Nullable
    BooksList findFirstByUidAndListIdGreaterThanEqual(String uid, int listIdGreaterThan);

    @Nullable
    BooksList findFirstByListIdAndUidOrUidNull(int listId, String uid);

    @Nullable
    BooksList findFirstByListIdAndUidEquals(int listId, String uid);

    @Nullable
    BooksList findFirstByUidAndName(String uid, String name);

    @Transactional
    long deleteByListIdAndUid(int listId, String uid);
}
