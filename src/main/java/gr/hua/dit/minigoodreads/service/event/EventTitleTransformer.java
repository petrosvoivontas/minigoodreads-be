package gr.hua.dit.minigoodreads.service.event;

import java.util.Map;

public interface EventTitleTransformer {

    String createTitle(String eventName, Map<String, Object> eventParams);

    String createSubtitle(String eventName, Map<String, Object> eventParams);
}
