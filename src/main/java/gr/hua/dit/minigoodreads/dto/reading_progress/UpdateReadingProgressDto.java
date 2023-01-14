package gr.hua.dit.minigoodreads.dto.reading_progress;

import jakarta.validation.constraints.Min;

public record UpdateReadingProgressDto(
    @Min(
        value = 1,
        message = "{reading_progress.current_page.notempty}"
    )
    int currentPage
) {
}
