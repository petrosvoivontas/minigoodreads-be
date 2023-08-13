package gr.hua.dit.minigoodreads.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.hua.dit.minigoodreads.events.EventConstants;
import gr.hua.dit.minigoodreads.validation.StringRange;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record PostEventDto(

	@StringRange(
		range = {
			EventConstants.EVENT_LIST_CREATE,
			EventConstants.EVENT_LIST_DELETE,
			EventConstants.EVENT_LIST_RENAME,
			EventConstants.EVENT_BOOK_IN_LIST_ADD,
			EventConstants.EVENT_BOOK_IN_LIST_REMOVE,
			EventConstants.EVENT_READING_PROGRESS_UPDATE
		},
		message = "{event.name.invalid}"
	)
	@JsonProperty("eventName")
	String eventName,

	@NotNull(message = "{event.params.null}")
	@JsonProperty("eventParams")
	Map<String, Object> eventParams
) {
}
