package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.controller.book_in_list.BookInListErrors;
import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface BookInListService {

    Result<BookInList, BookInListErrors> addBookToList(@NotNull String uid, @NotNull AddBookInListDto addBookInListDto);

    Result<Set<BookInList>, BookInListErrors> getBooksInList(@NotNull String uid, int listId);

    Result<Void, BookInListErrors> removeBookFromList(@NotNull String uid, int listId, @NotNull String bookId);
}
