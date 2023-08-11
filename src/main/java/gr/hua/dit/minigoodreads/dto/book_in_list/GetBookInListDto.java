package gr.hua.dit.minigoodreads.dto.book_in_list;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetBookInListDto(
    @JsonProperty("bookId") String bookId,
    @JsonProperty("coverImageUrl") String coverImageUrl,
    @JsonProperty("bookTitle") String bookTitle,
    @JsonProperty("bookAuthor") String bookAuthor,
	@JsonProperty("pageCount") int pageCount,
    @JsonProperty("insertTs") long insertTs
) {
}
