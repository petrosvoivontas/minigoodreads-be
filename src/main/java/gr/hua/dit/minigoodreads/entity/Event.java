package gr.hua.dit.minigoodreads.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@Column(name = "resource_id", length = 40)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String resourceId;

	@Column(name = "uid", length = 40, nullable = false)
	private String uid;

	@Column(name = "event_name", nullable = false)
	@Enumerated(EnumType.STRING)
	private EventNames eventName;

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
		EventNames eventName,
		Integer listId,
		String bookId,
		String imageUrl
	) {
		this.uid = uid;
		this.eventName = eventName;
		this.listId = listId;
		this.bookId = bookId;
		this.imageUrl = imageUrl;
	}

	public EventNames getEventName() {
		return eventName;
	}

	public Integer getListId() {
		return listId;
	}

	public String getBookId() {
		return bookId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Date getInsertTs() {
		return insertTs;
	}
}
