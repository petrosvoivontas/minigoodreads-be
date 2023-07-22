package gr.hua.dit.minigoodreads.service.event;

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
import java.util.Map;
import java.util.Objects;
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

	@Nullable
	private Event createBookSearchEvent(PostEventDto dto, EventNames eventName, String username) {
		Map<String, Object> params = dto.eventParams();
		String bookId = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_BOOK_ID),
			null
		);
		String bookTitle = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_BOOK_TITLE),
			null
		);
		String bookAuthor = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_BOOK_AUTHOR),
			null
		);
		String imageUrl = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_IMAGE_URL),
			null
		);
		if (bookId == null || bookTitle == null || bookAuthor == null) {
			return null;
		}
		return new Event(
			username,
			eventName,
			null,
			bookId,
			imageUrl
		);
	}

	@Nullable
	private Event createListCreateEvent(PostEventDto dto, EventNames eventName, String username) {
		Map<String, Object> params = dto.eventParams();
		Object listIdObj = params.get(EventConstants.EVENT_PARAM_LIST_ID);
		String listName = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_LIST_NAME),
			null
		);
		if (!(listIdObj instanceof Integer listId) || listName == null) {
			return null;
		}
		return new Event(
			username,
			eventName,
			listId,
			null,
			null
		);
	}

	@Nullable
	private Event createListDeleteEvent(PostEventDto dto, EventNames eventName, String username) {
		String name = dto.eventName();
		Map<String, Object> params = dto.eventParams();
		String listName = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_LIST_NAME),
			null
		);
		if (listName == null) {
			return null;
		}
		return new Event(
			username,
			eventName,
			null,
			null,
			null
		);
	}

	@Nullable
	private Event createListRenameEvent(PostEventDto dto, EventNames eventName, String username) {
		String name = dto.eventName();
		Map<String, Object> params = dto.eventParams();
		Object listIdObj = params.get(EventConstants.EVENT_PARAM_LIST_ID);
		String oldName = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_LIST_OLD_NAME),
			null
		);
		String newName = Objects.toString(
			params.get(EventConstants.EVENT_PARAM_LIST_NEW_NAME),
			null
		);
		if (!(listIdObj instanceof Integer listId) || oldName == null || newName == null) {
			return null;
		}
		return new Event(
			username,
			eventName,
			listId,
			null,
			null
		);
	}

	@Nullable
	private Event createAddBookToListEvent(PostEventDto dto) {
		return null;
	}

	@Nullable
	private Event createRemoveBookFromListEvent(PostEventDto dto) {
		return null;
	}

	@Nullable
	private Event createUpdateProgressEvent(PostEventDto dto) {
		return null;
	}

	@Override
	public Result<Void, EventErrors> postEvent(PostEventDto dto, String username) {
		@Nullable final Event event = switch (dto.eventName()) {
			case EventConstants.EVENT_BOOK_SEARCH -> createBookSearchEvent(dto, EventNames.EVENT_BOOK_SEARCH, username);
			case EventConstants.EVENT_LIST_CREATE -> createListCreateEvent(dto, EventNames.EVENT_LIST_CREATE, username);
			case EventConstants.EVENT_LIST_DELETE -> createListDeleteEvent(dto, EventNames.EVENT_LIST_DELETE, username);
			case EventConstants.EVENT_LIST_RENAME -> createListRenameEvent(dto, EventNames.EVENT_LIST_RENAME, username);
			case EventConstants.EVENT_BOOK_IN_LIST_ADD -> createAddBookToListEvent(dto);
			case EventConstants.EVENT_BOOK_IN_LIST_REMOVE -> createRemoveBookFromListEvent(dto);
			case EventConstants.EVENT_READING_PROGRESS_UPDATE -> createUpdateProgressEvent(dto);
			default -> null;
		};
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
