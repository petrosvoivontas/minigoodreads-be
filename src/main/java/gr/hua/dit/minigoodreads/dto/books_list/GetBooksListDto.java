package gr.hua.dit.minigoodreads.dto.books_list;

public class GetBooksListDto {

    private String resourceId;

    private int listId;

    private String name;

    public GetBooksListDto() {
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getListId() {
        return listId;
    }

    public String getName() {
        return name;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
