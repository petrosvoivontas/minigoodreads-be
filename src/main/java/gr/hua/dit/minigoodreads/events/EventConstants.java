package gr.hua.dit.minigoodreads.events;

public class EventConstants {

    // region event types
    public static final String EVENT_LIST_CREATE = "list_create";
    public static final String EVENT_LIST_DELETE = "list_delete";
    public static final String EVENT_LIST_RENAME = "list_rename";

    public static final String EVENT_BOOK_IN_LIST_ADD = "book_in_list_add";
    public static final String EVENT_BOOK_IN_LIST_REMOVE = "book_in_list_remove";

    public static final String EVENT_READING_PROGRESS_UPDATE = "reading_progress_update";
    // endregion

    // region event params
    public static final String EVENT_PARAM_BOOK_ID = "bookId";
    public static final String EVENT_PARAM_BOOK_TITLE = "bookTitle";
    public static final String EVENT_PARAM_BOOK_AUTHOR = "bookAuthor";
    public static final String EVENT_PARAM_IMAGE_URL = "imageUrl";
    public static final String EVENT_PARAM_LIST_ID = "listId";
    public static final String EVENT_PARAM_LIST_NAME = "listName";
    public static final String EVENT_PARAM_LIST_OLD_NAME = "listOldName";
    public static final String EVENT_PARAM_LIST_NEW_NAME = "listNewName";
    public static final String EVENT_PARAM_CURRENT_PAGE = "currentPage";
    public static final String EVENT_PARAM_TOTAL_PAGES = "totalPages";
    // endregion
}
