package gr.hua.dit.minigoodreads.service.reading_progress;

import gr.hua.dit.minigoodreads.controller.reading_progress.ReadingProgressErrors;
import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import gr.hua.dit.minigoodreads.service.Result;
import org.jetbrains.annotations.NotNull;

public interface ReadingProgressService {

    @NotNull
    Result<ReadingProgress, ReadingProgressErrors> getReadingProgress(
        @NotNull String bookId,
        @NotNull String uid
    );

    @NotNull
    Result<Void, ReadingProgressErrors> updateReadingProgress(
        @NotNull String bookId,
        int currentPage,
        @NotNull String uid
    );
}
