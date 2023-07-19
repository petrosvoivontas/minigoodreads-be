package gr.hua.dit.minigoodreads.dto.books_list;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record RenameBooksListDto(
	@NotBlank(message = "New name cannot be empty")
	@JsonProperty("name")
	String name
) {
}
