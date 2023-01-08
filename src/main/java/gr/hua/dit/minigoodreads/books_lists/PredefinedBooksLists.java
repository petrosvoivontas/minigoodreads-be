package gr.hua.dit.minigoodreads.books_lists;

public enum PredefinedBooksLists {
    WANT_TO_READ(0, "Want to read"),
    CURRENTLY_READING(1, "Currently reading"),
    READ(2, "Read");

    private final int listId;
    private final String name;

    PredefinedBooksLists(int listId, String name) {
        this.listId = listId;
        this.name = name;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }
}
