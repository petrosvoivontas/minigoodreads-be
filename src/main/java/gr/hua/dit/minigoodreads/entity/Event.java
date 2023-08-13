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

	@Column(name = "event_params")
	private String eventParams;

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
		String eventParams
	) {
		this.uid = uid;
		this.eventName = eventName;
		this.eventParams = eventParams;
	}

	public EventNames getEventName() {
		return eventName;
	}

	public String getEventParams() {
		return eventParams;
	}

	public Date getInsertTs() {
		return insertTs;
	}
}
