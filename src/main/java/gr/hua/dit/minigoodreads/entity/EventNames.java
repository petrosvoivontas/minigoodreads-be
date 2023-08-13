package gr.hua.dit.minigoodreads.entity;

import gr.hua.dit.minigoodreads.events.EventConstants;

public enum EventNames {
	EVENT_LIST_CREATE(EventConstants.EVENT_LIST_CREATE),
	EVENT_LIST_DELETE(EventConstants.EVENT_LIST_DELETE),
	EVENT_LIST_RENAME(EventConstants.EVENT_LIST_RENAME),
	EVENT_BOOK_IN_LIST_ADD(EventConstants.EVENT_BOOK_IN_LIST_ADD),
	EVENT_BOOK_IN_LIST_REMOVE(EventConstants.EVENT_BOOK_IN_LIST_REMOVE),
	EVENT_READING_PROGRESS_UPDATE(EventConstants.EVENT_READING_PROGRESS_UPDATE);

	private final String eventName;

	EventNames(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}
}
