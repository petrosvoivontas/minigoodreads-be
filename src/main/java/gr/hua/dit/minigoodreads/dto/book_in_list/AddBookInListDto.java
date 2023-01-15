package gr.hua.dit.minigoodreads.dto.book_in_list;

import jakarta.validation.constraints.NotBlank;

public class AddBookInListDto {

    private int listId;

    @NotBlank(message = "{book_in_list.add.book_id.empty}")
    private String bookId;

    private String coverImageUrl;

    @NotBlank(message = "{book_in_list.add.book_title.empty}")
    private String bookTitle;

    @NotBlank(message = "{book_in_list.add.book_author.empty}")
    private String bookAuthor;

    public AddBookInListDto() {
    }

    public int getListId() {
        return listId;
    }

    public String getBookId() {
        return bookId;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}
