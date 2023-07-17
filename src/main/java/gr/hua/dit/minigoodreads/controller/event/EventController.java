package gr.hua.dit.minigoodreads.controller.event;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.service.Result;
import gr.hua.dit.minigoodreads.service.event.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController extends BaseController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    ResponseEntity<Void> postEvent(@Valid @RequestBody PostEventDto dto) {
        Result<Void, EventErrors> result = eventService.postEvent(dto);
        return switch (result) {
            case Result.Success<Void, EventErrors> ignored -> ResponseEntity.ok().build();
            case Result.Error<Void, EventErrors> error -> throw handleError(error.getError());
        };
    }
}
