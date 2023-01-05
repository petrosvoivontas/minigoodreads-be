package gr.hua.dit.minigoodreads.dto.books_list;

import jakarta.validation.constraints.NotBlank;

public class CreateBooksListDto {

    @NotBlank(message = "A list must have a non-empty name")
    private String name;

    public CreateBooksListDto() {
    }

    public String getName() {
        return name;
    }
}
