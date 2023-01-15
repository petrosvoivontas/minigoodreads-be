package gr.hua.dit.minigoodreads.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventTitleTransformerImpl implements EventTitleTransformer {

    private final MessageSource messageSource;

    @Autowired
    public EventTitleTransformerImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String createTitle(
        String eventName,
        Map<String, Object> eventParams
    ) {
        return "";
    }

    @Override
    public String createSubtitle(
        String eventName,
        Map<String, Object> eventParams
    ) {
        return "";
    }
}
