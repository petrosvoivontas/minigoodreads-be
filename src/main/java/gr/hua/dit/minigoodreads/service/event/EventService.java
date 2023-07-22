package gr.hua.dit.minigoodreads.service.event;

import gr.hua.dit.minigoodreads.controller.event.EventErrors;
import gr.hua.dit.minigoodreads.dto.event.GetEventDto;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.service.Result;

import java.util.List;

public interface EventService {

	Result<Void, EventErrors> postEvent(PostEventDto dto, String username);

	Result<List<GetEventDto>, EventErrors> getEvents(String username);
}
