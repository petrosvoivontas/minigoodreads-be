package gr.hua.dit.minigoodreads.dto.book_in_list;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record AddBookInListDto(
	@NotBlank(message = "{book_in_list.add.book_id.empty}")
	@JsonProperty("bookId")
	String bookId,
	@JsonProperty("coverImageUrl")
	String coverImageUrl,
	@NotBlank(message = "{book_in_list.add.book_title.empty}")
	@JsonProperty("bookTitle")
	String bookTitle,
	@NotBlank(message = "{book_in_list.add.book_author.empty}")
	@JsonProperty("bookAuthor")
	String bookAuthor
) {
}
