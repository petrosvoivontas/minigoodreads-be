package gr.hua.dit.minigoodreads.dto.books_list;

import jakarta.validation.constraints.NotBlank;

public class RenameBooksListDto {

    @NotBlank(message = "New name cannot be empty")
    private String name;

    public RenameBooksListDto() {
    }

    public String getName() {
        return name;
    }
}
