package gr.hua.dit.minigoodreads.dto.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record GetEventDto(
	@JsonProperty("eventName")
	String eventName,
	@JsonProperty("eventParams")
	Map<String, Object> eventParams,
	@JsonProperty("insertAt")
	long insertAtMs
) {
}
