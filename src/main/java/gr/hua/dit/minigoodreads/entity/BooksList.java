package gr.hua.dit.minigoodreads.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "books_list",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uc_id_uid",
            columnNames = {"list_id", "uid"}
        ),
        @UniqueConstraint(
            name = "uc_uid_name",
            columnNames = {"uid", "name"}
        )
    }
)
public class BooksList {

    @Id
    @Column(name = "resource_id", length = 40)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resourceId;

    @Column(name = "list_id", columnDefinition = "SMALLINT", nullable = false)
    private int listId;

    @Column(name = "uid", length = 40, nullable = false)
    private String uid;

    @Column(
        name = "name",
        length = 100,
        nullable = false
    )
    private String name;

    public BooksList() {
    }

    public BooksList(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getResourceId() {
        return resourceId;
    }

    public int getListId() {
        return listId;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
