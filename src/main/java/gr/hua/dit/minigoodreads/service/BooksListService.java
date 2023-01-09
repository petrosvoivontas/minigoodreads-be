package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.controller.bookslist.BooksListErrors;
import gr.hua.dit.minigoodreads.entity.BooksList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface BooksListService {

    Result.Success<Set<BooksList>, BooksListErrors> getListsForUser(@NotNull String uid);

    Result<BooksList, BooksListErrors> saveList(@NotNull BooksList list);

    Result<Void, BooksListErrors> renameList(int listId, @NotNull String uid, @NotNull String newName);

    Result<Void, BooksListErrors> deleteList(int listId, @NotNull String uid);
}
