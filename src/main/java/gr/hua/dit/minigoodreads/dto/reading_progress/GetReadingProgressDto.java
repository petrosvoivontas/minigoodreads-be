package gr.hua.dit.minigoodreads.dto.reading_progress;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetReadingProgressDto(
    @JsonProperty("currentPage")
    int currentPage,
    @JsonProperty("lastUpdated")
    long lastUpdated
) {
}
