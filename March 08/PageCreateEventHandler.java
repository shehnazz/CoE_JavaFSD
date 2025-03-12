package com.mytraining.core.listeners;

import org.apache.sling.api.SlingConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = EventHandler.class,
    immediate = true,
    property = {
        "event.topics=" + SlingConstants.TOPIC_RESOURCE_ADDED
    }
)
public class PageCreateEventHandler implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PageCreateEventHandler.class);

    @Override
    public void handleEvent(Event event) {
        String path = (String) event.getProperty(SlingConstants.PROPERTY_PATH);
        LOG.info("New Page Created: {}", path);
    }
}
