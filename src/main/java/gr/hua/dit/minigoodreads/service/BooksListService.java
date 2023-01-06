package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.entity.BooksList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface BooksListService {

    List<BooksList> getListsForUser(@NotNull String uid);

    BooksList saveList(@NotNull BooksList list);
}
