package gr.hua.dit.minigoodreads.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
	name = "reading_progress"
)
public class ReadingProgress {

	@Id
	@Column(name = "resource_id", length = 40)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String resourceId;

	@Column(
		name = "current_page",
		columnDefinition = "SMALLINT",
		nullable = false
	)
	private int currentPage;

	@Column(name = "last_updated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@OneToOne
	@JoinColumn(name = "book_in_list_id", referencedColumnName = "resource_id")
	private BookInList bookInList;

	@PrePersist
	@PreUpdate
	private void onInsert() {
		this.lastUpdated = new Date();
	}

	public ReadingProgress() {
	}

	public ReadingProgress(int currentPage, BookInList bookInList) {
		this.currentPage = currentPage;
		this.bookInList = bookInList;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
