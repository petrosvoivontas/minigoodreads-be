package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.controller.bookslist.BooksListErrors;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.repository.BooksListRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BooksListServiceImpl implements BooksListService {

    private static final int FIRST_CUSTOM_LIST_ID = 10;
    private static final int CUSTOM_LIST_ID_INCREMENT = 1;

    private final BooksListRepository repository;

    public BooksListServiceImpl(BooksListRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result.Success<Set<BooksList>, BooksListErrors> getListsForUser(@NotNull String uid) {
        Set<BooksList> lists = repository.findBooksListsByUidEqualsOrderByListIdAsc(uid);
        return new Result.Success<>(lists);
    }

    @Override
    public Result.Success<BooksList, BooksListErrors> saveList(@NotNull BooksList list) {
        BooksList lastAddedList = repository.findFirstByUidEqualsOrderByListIdDesc(list.getUid());
        final int listId;
        if (lastAddedList != null) {
            listId = lastAddedList.getListId() + CUSTOM_LIST_ID_INCREMENT;
        } else {
            listId = FIRST_CUSTOM_LIST_ID;
        }
        list.setListId(listId);
        return new Result.Success<>(repository.save(list));
    }

    @Override
    public Result<Void, BooksListErrors> renameList(int listId, @NotNull String uid, @NotNull String newName) {
        BooksList list = repository.findFirstByUidAndListId(uid, listId);
        if (list != null) {
            list.setName(newName);
            repository.save(list);
            return new Result.Success<>();
        }
        return new Result.Error<>(BooksListErrors.LIST_NOT_FOUND);
    }

    @Override
    public Result<Void, BooksListErrors> deleteList(int listId, @NotNull String uid) {
        try {
            long listsDeleted = repository.deleteByListIdAndUid(listId, uid);
            return listsDeleted > 0 ? new Result.Success<>() : new Result.Error<>(BooksListErrors.COULD_NOT_DELETE_LIST);
        } catch (Exception e) {
            return new Result.Error<>(BooksListErrors.COULD_NOT_DELETE_LIST);
        }
    }
}
