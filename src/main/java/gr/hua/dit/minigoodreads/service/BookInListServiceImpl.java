package gr.hua.dit.minigoodreads.service;

import gr.hua.dit.minigoodreads.controller.book_in_list.BookInListErrors;
import gr.hua.dit.minigoodreads.dto.book_in_list.AddBookInListDto;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.entity.BooksList;
import gr.hua.dit.minigoodreads.repository.BookInListRepository;
import gr.hua.dit.minigoodreads.repository.BooksListRepository;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BookInListServiceImpl implements BookInListService {

    private final BookInListRepository bookInListRepository;
    private final BooksListRepository booksListRepository;
    private final ModelMapper modelMapper;

    public BookInListServiceImpl(BookInListRepository bookInListRepository, BooksListRepository booksListRepository, ModelMapper modelMapper) {
        this.bookInListRepository = bookInListRepository;
        this.booksListRepository = booksListRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result<BookInList, BookInListErrors> addBookToList(@NotNull String uid, @NotNull AddBookInListDto addBookInListDto) {
        BooksList list = booksListRepository.findFirstByListIdAndUidOrUidNull(addBookInListDto.getListId(), uid);
        if (list == null) {
            return new Result.Error<>(BookInListErrors.LIST_NOT_FOUND);
        }
        BookInList bookInList = modelMapper.map(addBookInListDto, BookInList.class);
        bookInList.setUid(uid);
        bookInList.setBooksList(list);
        BookInList savedList = bookInListRepository.save(bookInList);
        return new Result.Success<>(savedList);
    }

    @Override
    public Result<Set<BookInList>, BookInListErrors> getBooksInList(@NotNull String uid, int listId) {
        BooksList list = booksListRepository.findFirstByListIdAndUidOrUidNull(listId, uid);
        if (list == null) {
            return new Result.Error<>(BookInListErrors.LIST_NOT_FOUND);
        }
        Set<BookInList> booksInList = bookInListRepository.findByUidAndBooksListResourceId(uid, list.getResourceId());
        return new Result.Success<>(booksInList);
    }

    @Override
    public Result<Void, BookInListErrors> removeBookFromList(@NotNull String uid, int listId, @NotNull String bookId) {
        BooksList list = booksListRepository.findFirstByListIdAndUidOrUidNull(listId, uid);
        if (list == null) {
            return new Result.Error<>(BookInListErrors.LIST_NOT_FOUND);
        }
        long rowsDeleted = bookInListRepository.deleteByUidAndBooksListResourceIdAndBookId(uid, list.getResourceId(), bookId);
        return rowsDeleted > 0 ? new Result.Success<>() : new Result.Error<>(BookInListErrors.BOOK_NOT_REMOVED_FROM_LIST);
    }
}
