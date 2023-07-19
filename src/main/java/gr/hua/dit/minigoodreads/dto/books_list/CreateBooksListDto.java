package gr.hua.dit.minigoodreads.dto.books_list;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record CreateBooksListDto(
	@NotBlank(message = "{list.name.notempty}")
	@JsonProperty("name")
	String name
) {
}
