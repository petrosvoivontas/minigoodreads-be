package gr.hua.dit.minigoodreads.repository;

import gr.hua.dit.minigoodreads.entity.ReadingProgress;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, String> {

    @Nullable
    ReadingProgress findFirstByBookInListResourceId(String bookInListId);
}
