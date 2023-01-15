package gr.hua.dit.minigoodreads.dto.event;

import gr.hua.dit.minigoodreads.events.EventConstants;
import gr.hua.dit.minigoodreads.validation.StringRange;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record PostEventDto(

    @StringRange(
        range = {
            EventConstants.EVENT_BOOK_SEARCH,
            EventConstants.EVENT_LIST_CREATE,
            EventConstants.EVENT_LIST_DELETE,
            EventConstants.EVENT_LIST_RENAME,
            EventConstants.EVENT_BOOK_IN_LIST_ADD,
            EventConstants.EVENT_BOOK_IN_LIST_REMOVE,
            EventConstants.EVENT_READING_PROGRESS_UPDATE
        },
        message = "{event.name.invalid}"
    )
    String eventName,

    @NotNull(message = "{event.params.null}")
    Map<String, Object> eventParams
) {
}
