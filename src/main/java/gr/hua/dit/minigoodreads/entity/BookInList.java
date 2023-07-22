package gr.hua.dit.minigoodreads.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
	name = "book_in_list",
	uniqueConstraints = {@UniqueConstraint(
		name = "uc_book_id_uid",
		columnNames = {"book_id", "uid", "list_resource_id"}
	)}
)
public class BookInList {

	@Id
	@Column(name = "resource_id", length = 40)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String resourceId;

	@Column(name = "uid", length = 40, nullable = false)
	private String uid;

	@Column(name = "book_id", length = 100, nullable = false)
	private String bookId;

	@Column(name = "cover_image_url")
	private String coverImageUrl;

	@Column(name = "book_title")
	private String bookTitle;

	@Column(name = "book_author")
	private String bookAuthor;

	@Column(name = "insert_ts")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertTs;

	@ManyToOne
	@JoinColumn(
		name = "list_resource_id",
		nullable = false,
		foreignKey = @ForeignKey(name = "fk_books_list")
	)
	private BooksList booksList;

	@OneToOne(mappedBy = "bookInList", cascade = CascadeType.REMOVE)
	private ReadingProgress readingProgress;

	public BookInList() {
	}

	public BookInList(
		String bookId,
		String coverImageUrl,
		String bookTitle,
		String bookAuthor
	) {
		this.bookId = bookId;
		this.coverImageUrl = coverImageUrl;
		this.bookTitle = bookTitle;
		this.bookAuthor = bookAuthor;
	}

	@PrePersist
	private void onInsert() {
		this.insertTs = new Date();
	}

	public String getResourceId() {
		return resourceId;
	}

	public String getUid() {
		return uid;
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

	public Date getInsertTs() {
		return insertTs;
	}

	public BooksList getBooksList() {
		return booksList;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setBooksList(BooksList booksList) {
		this.booksList = booksList;
	}
}
