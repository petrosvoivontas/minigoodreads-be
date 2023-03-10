package gr.hua.dit.minigoodreads.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "resource_id", length = 40)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resourceId;

    @Column(name = "uid", length = 40, nullable = false)
    private String uid;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "event_subtitle", nullable = false)
    private String eventSubtitle;

    @Column(name = "list_resource_id", columnDefinition = "SMALLINT", length = 40)
    private Integer listId;

    @Column(name = "book_id", length = 100)
    private String bookId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "insert_ts", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTs;

    @PrePersist
    private void onInsert() {
        this.insertTs = new Date();
    }

    public Event() {
    }

    public Event(
        String uid,
        String eventTitle,
        String eventSubtitle,
        Integer listId,
        String bookId,
        String imageUrl
    ) {
        this.uid = uid;
        this.eventTitle = eventTitle;
        this.eventSubtitle = eventSubtitle;
        this.listId = listId;
        this.bookId = bookId;
        this.imageUrl = imageUrl;
    }
}
