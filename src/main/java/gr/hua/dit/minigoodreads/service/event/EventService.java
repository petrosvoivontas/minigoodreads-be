package gr.hua.dit.minigoodreads.service.event;

import gr.hua.dit.minigoodreads.controller.event.EventErrors;
import gr.hua.dit.minigoodreads.dto.event.PostEventDto;
import gr.hua.dit.minigoodreads.service.Result;

public interface EventService {

    Result<Void, EventErrors> postEvent(PostEventDto dto, String username);
}
