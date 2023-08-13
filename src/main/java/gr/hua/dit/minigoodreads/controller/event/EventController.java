package gr.hua.dit.minigoodreads.controller.event;

import gr.hua.dit.minigoodreads.controller.BaseController;
import gr.hua.dit.minigoodreads.controller.ResponseWrapper;
import gr.hua.dit.minigoodreads.dto.event.GetEventDto;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.service.Result;
import gr.hua.dit.minigoodreads.service.event.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class EventController extends BaseController {

	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping
	ResponseEntity<Void> postEvent(@Valid @RequestBody PostEventDto dto, Principal principal) {
		Result<Void, EventErrors> result = eventService.postEvent(dto, principal.getName());
		return switch (result) {
			case Result.Success<Void, EventErrors> ignored -> ResponseEntity.ok().build();
			case Result.Error<Void, EventErrors> error -> throw handleError(error.getError());
		};
	}

	@GetMapping
	ResponseEntity<ResponseWrapper<List<GetEventDto>>> getEvents(Principal principal) {
		Result<List<GetEventDto>, EventErrors> result = eventService.getEvents(principal.getName());
		return switch (result) {
			case Result.Success<List<GetEventDto>, EventErrors> s ->
				ResponseEntity.ok(new ResponseWrapper<>(s.getData()));
			case Result.Error<List<GetEventDto>, EventErrors> error -> throw handleError(error.getError());
		};
	}
}
