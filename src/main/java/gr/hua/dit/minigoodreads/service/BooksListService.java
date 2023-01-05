package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.entity.BooksList;

import java.util.List;

public interface BooksListService {

    List<BooksList> getListsForUser(String uid);

    BooksList saveList(BooksList list);
}
