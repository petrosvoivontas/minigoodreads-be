package gr.hua.dit.minigoodreads.dto.book_in_list;

public class AddBookInListDto {

    private int listId;

    private String bookId;

    private String coverImageUrl;

    private String bookTitle;

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
