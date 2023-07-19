package gr.hua.dit.minigoodreads.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Set;

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
public class BooksList implements Comparable<BooksList> {

    @Id
    @Column(name = "resource_id", length = 40)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String resourceId;

    @Column(name = "list_id", columnDefinition = "SMALLINT", nullable = false)
    private int listId;

    @Column(name = "uid", length = 40, nullable = true)
    private String uid;

    @Column(
        name = "name",
        length = 100,
        nullable = false
    )
    private String name;

    @OneToMany(mappedBy = "booksList", cascade = CascadeType.REMOVE)
    private Set<BookInList> booksInList;

    public BooksList() {
    }

    public BooksList(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public BooksList(int listId, String name) {
        this.listId = listId;
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

    @Override
    public int compareTo(@NotNull BooksList other) {
        return listId - other.listId;
    }
}
