package gr.hua.dit.minigoodreads.service.event;

import gr.hua.dit.minigoodreads.controller.event.EventErrors;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.entity.Event;
import gr.hua.dit.minigoodreads.events.EventConstants;
import gr.hua.dit.minigoodreads.repository.EventRepository;
import gr.hua.dit.minigoodreads.service.Result;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class EventServiceImpl implements EventService {

    private final EventTitleTransformerImpl titleTransformer;
    private final EventRepository eventRepository;

    public EventServiceImpl(
        EventTitleTransformerImpl titleTransformer,
        EventRepository eventRepository
    ) {
        this.titleTransformer = titleTransformer;
        this.eventRepository = eventRepository;
    }

    @Nullable
    private Event createBookSearchEvent(PostEventDto dto) {
        String name = dto.eventName();
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
        String title = titleTransformer.createTitle(name, params);
        String subtitle = titleTransformer.createSubtitle(name, params);
        return new Event(
            "uid",
            title,
            subtitle,
            null,
            bookId,
            imageUrl
        );
    }

    @Nullable
    private Event createListCreateEvent(PostEventDto dto) {
        String name = dto.eventName();
        Map<String, Object> params = dto.eventParams();
        Object listIdObj = params.get(EventConstants.EVENT_PARAM_LIST_ID);
        String listName = Objects.toString(
            params.get(EventConstants.EVENT_PARAM_LIST_NAME),
            null
        );
        if (!(listIdObj instanceof Integer listId) || listName == null) {
            return null;
        }
        String title = titleTransformer.createTitle(name, params);
        String subtitle = titleTransformer.createSubtitle(name, params);
        return new Event(
            "uid",
            title,
            subtitle,
            listId,
            null,
            null
        );
    }

    @Nullable
    private Event createListDeleteEvent(PostEventDto dto) {
        String name = dto.eventName();
        Map<String, Object> params = dto.eventParams();
        String listName = Objects.toString(
            params.get(EventConstants.EVENT_PARAM_LIST_NAME),
            null
        );
        if (listName == null) {
            return null;
        }
        String title = titleTransformer.createTitle(name, params);
        String subtitle = titleTransformer.createSubtitle(name, params);
        return new Event(
            "uid",
            title,
            subtitle,
            null,
            null,
            null
        );
    }

    @Nullable
    private Event createListRenameEvent(PostEventDto dto) {
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
        String title = titleTransformer.createTitle(name, params);
        String subtitle = titleTransformer.createSubtitle(name, params);
        return new Event(
            "uid",
            title,
            subtitle,
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
    public Result<Void, EventErrors> postEvent(PostEventDto dto) {
        @Nullable final Event event = switch (dto.eventName()) {
            case EventConstants.EVENT_BOOK_SEARCH -> createBookSearchEvent(dto);
            case EventConstants.EVENT_LIST_CREATE -> createListCreateEvent(dto);
            case EventConstants.EVENT_LIST_DELETE -> createListDeleteEvent(dto);
            case EventConstants.EVENT_LIST_RENAME -> createListRenameEvent(dto);
            case EventConstants.EVENT_BOOK_IN_LIST_ADD ->
                createAddBookToListEvent(dto);
            case EventConstants.EVENT_BOOK_IN_LIST_REMOVE ->
                createRemoveBookFromListEvent(dto);
            case EventConstants.EVENT_READING_PROGRESS_UPDATE ->
                createUpdateProgressEvent(dto);
            default -> null;
        };
        if (event == null) {
            return new Result.Error<>(EventErrors.INVALID_EVENT_PARAMS);
        }
        eventRepository.save(event);
        return new Result.Success<>();
    }
}
