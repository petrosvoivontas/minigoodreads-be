package gr.hua.dit.minigoodreads.dto.books_list;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetBooksListDto(
    @JsonProperty("resourceId")
    String resourceId,
    @JsonProperty("listId")
    int listId,
    @JsonProperty("name")
    String name
) {
}
