package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.repository.BooksListRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksListServiceImpl implements BooksListService {

    private static final int FIRST_CUSTOM_LIST_ID = 10;
    private static final int CUSTOM_LIST_ID_INCREMENT = 1;

    private final BooksListRepository repository;

    public BooksListServiceImpl(BooksListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BooksList> getListsForUser(@NotNull String uid) {
        return repository.findBooksListsByUidEqualsOrderByListIdAsc(uid);
    }

    @Override
    public BooksList saveList(@NotNull BooksList list) {
        BooksList lastAddedList = repository.findFirstByUidEqualsOrderByListIdDesc(list.getUid());
        final int listId;
        if (lastAddedList != null) {
            listId = lastAddedList.getListId() + CUSTOM_LIST_ID_INCREMENT;
        } else {
            listId = FIRST_CUSTOM_LIST_ID;
        }
        list.setListId(listId);
        return repository.save(list);
    }
}
