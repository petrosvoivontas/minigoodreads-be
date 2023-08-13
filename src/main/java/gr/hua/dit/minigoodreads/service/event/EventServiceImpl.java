package gr.hua.dit.minigoodreads.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.dit.minigoodreads.controller.event.EventErrors;
import gr.hua.dit.minigoodreads.dto.event.GetEventDto;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.entity.Event;
import gr.hua.dit.minigoodreads.entity.EventNames;
import gr.hua.dit.minigoodreads.events.EventConstants;
import gr.hua.dit.minigoodreads.repository.EventRepository;
import gr.hua.dit.minigoodreads.service.Result;
import org.jetbrains.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result<Void, EventErrors> postEvent(PostEventDto dto, String username) {
		Event event = null;
		@Nullable final EventNames eventName = switch (dto.eventName()) {
			case EventConstants.EVENT_LIST_CREATE -> EventNames.EVENT_LIST_CREATE;
			case EventConstants.EVENT_LIST_DELETE -> EventNames.EVENT_LIST_DELETE;
			case EventConstants.EVENT_LIST_RENAME -> EventNames.EVENT_LIST_RENAME;
			case EventConstants.EVENT_BOOK_IN_LIST_ADD -> EventNames.EVENT_BOOK_IN_LIST_ADD;
			case EventConstants.EVENT_BOOK_IN_LIST_REMOVE -> EventNames.EVENT_BOOK_IN_LIST_REMOVE;
			case EventConstants.EVENT_READING_PROGRESS_UPDATE -> EventNames.EVENT_READING_PROGRESS_UPDATE;
			default -> null;
		};
		if (eventName == null) {
			return new Result.Error<>(EventErrors.INVALID_EVENT_NAME);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			event = new Event(username,
				eventName,
				mapper.writeValueAsString(dto.eventParams())
			);
		} catch (Exception e) {
			/* noop */
		}
		if (event == null) {
			return new Result.Error<>(EventErrors.INVALID_EVENT_PARAMS);
		}
		eventRepository.save(event);
		return new Result.Success<>();
	}

	@Override
	public Result<List<GetEventDto>, EventErrors> getEvents(String username) {
		Set<Event> events = eventRepository.findByUid(username);
		List<GetEventDto> eventDtoList = events
			.stream()
			.map(event -> modelMapper.map(event, GetEventDto.class))
			.collect(Collectors.toList());
		return new Result.Success<>(eventDtoList);
	}
}
