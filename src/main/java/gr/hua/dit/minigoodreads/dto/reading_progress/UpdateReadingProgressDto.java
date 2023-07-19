package gr.hua.dit.minigoodreads.dto.reading_progress;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public record UpdateReadingProgressDto(
	@Min(
		value = 1,
		message = "{reading_progress.current_page.notempty}"
	)
	@JsonProperty("currentPage")
	int currentPage
) {
}
