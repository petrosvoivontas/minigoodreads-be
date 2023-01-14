package gr.hua.dit.minigoodreads.service.reading_progress;

import gr.hua.dit.minigoodreads.books_lists.BooksListsConstants;
import gr.hua.dit.minigoodreads.controller.reading_progress.ReadingProgressErrors;
import gr.hua.dit.minigoodreads.entity.BookInList;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import gr.hua.dit.minigoodreads.repository.BookInListRepository;
import gr.hua.dit.minigoodreads.repository.ReadingProgressRepository;
import gr.hua.dit.minigoodreads.service.Result;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ReadingProgressServiceImpl implements ReadingProgressService {

    private final BookInListRepository bookInListRepository;
    private final ReadingProgressRepository readingProgressRepository;

    public ReadingProgressServiceImpl(
        BookInListRepository bookInListRepository,
        ReadingProgressRepository readingProgressRepository
    ) {
        this.bookInListRepository = bookInListRepository;
        this.readingProgressRepository = readingProgressRepository;
    }

    @Override
    public @NotNull Result<ReadingProgress, ReadingProgressErrors> getReadingProgress(
        @NotNull String bookId,
        @NotNull String uid
    ) {
        BookInList bookInList = bookInListRepository.findFirstByUidAndBooksListListIdAndBookId(
            uid,
            BooksListsConstants.CURRENTLY_READING_LIST_ID,
            bookId
        );
        if (bookInList == null) {
            return new Result.Error<>(ReadingProgressErrors.BOOK_NOT_FOUND_IN_LIST);
        }
        ReadingProgress readingProgress = readingProgressRepository.findFirstByBookInListResourceId(
            bookInList.getResourceId()
        );
        if (readingProgress != null) {
            return new Result.Success<>(readingProgress);
        }
        return new Result.Error<>(ReadingProgressErrors.NOT_FOUND);
    }

    @Override
    public @NotNull Result<Void, ReadingProgressErrors> updateReadingProgress(
        @NotNull String bookId,
        int currentPage,
        @NotNull String uid
    ) {
        BookInList bookInList = bookInListRepository.findFirstByUidAndBooksListListIdAndBookId(
            uid,
            BooksListsConstants.CURRENTLY_READING_LIST_ID,
            bookId
        );
        if (bookInList == null) {
            return new Result.Error<>(ReadingProgressErrors.BOOK_NOT_FOUND_IN_LIST);
        }
        ReadingProgress readingProgress = readingProgressRepository.findFirstByBookInListResourceId(
            bookInList.getResourceId()
        );
        if (readingProgress != null) {
            readingProgress.setCurrentPage(currentPage);
        } else {
            readingProgress = new ReadingProgress(currentPage, bookInList);
        }
        readingProgressRepository.save(readingProgress);
        return new Result.Success<>();
    }
}
