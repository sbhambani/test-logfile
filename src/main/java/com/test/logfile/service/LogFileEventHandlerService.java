package com.test.logfile.service;

import com.test.logfile.dto.LogFileEvent;
import com.test.logfile.entity.LogFileEventEntity;
import com.test.logfile.repository.LogFileEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogFileEventHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(LogFileEventHandlerService.class);

    private static final int EVENT_DURATION_THRESHOLD = 4;

    private final LogFileEventRepository logFileEventRepository;

    public LogFileEventHandlerService(LogFileEventRepository logFileEventRepository) {
        this.logFileEventRepository = logFileEventRepository;
    }

    public void handleLogFileEvent(LogFileEvent logFileEvent, long duration) {
        logger.debug("Received log file event={}", logFileEvent);
        LogFileEventEntity entity = new LogFileEventEntity();
        entity.setEventId(logFileEvent.getEventId());
        entity.setDuration(duration);
        entity.setType(logFileEvent.getType());
        entity.setHost(logFileEvent.getHost());
        boolean flagEvent = duration > EVENT_DURATION_THRESHOLD;
        if (flagEvent) {
            logger.warn("Event={} has taken more time to finish", logFileEvent.getEventId());
        }
        entity.setAlert(flagEvent);
        logFileEventRepository.save(entity);
    }
}
