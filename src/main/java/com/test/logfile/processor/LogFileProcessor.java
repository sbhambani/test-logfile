package com.test.logfile.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.logfile.dto.LogFileEvent;
import com.test.logfile.dto.LogFileEventState;
import com.test.logfile.service.LogFileEventHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogFileProcessor {

    private static final Logger logger = LoggerFactory.getLogger(LogFileProcessor.class);

    private final ObjectMapper objectMapper;
    private final LogFileEventHandlerService eventHandlerService;

    public LogFileProcessor(ObjectMapper objectMapper, LogFileEventHandlerService eventHandlerService) {
        this.objectMapper = objectMapper;
        this.eventHandlerService = eventHandlerService;
    }

    public void processLogFile(String logFilePath) {
        logger.info("Start processing log file={}", logFilePath);
        File logFile = new File(logFilePath);
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            Map<String, LogFileEvent> eventStartedMap = new HashMap<>();
            Map<String, LogFileEvent> eventFinishedMap = new HashMap<>();
            String log;
            while ((log = br.readLine()) != null) {
                logger.debug("Process Log={}", log);
                LogFileEvent logFileEvent = objectMapper.readValue(log, LogFileEvent.class);
                if (LogFileEventState.STARTED.getEventState().equals(logFileEvent.getState().getEventState())) {
                    LogFileEvent finishedEvent = eventFinishedMap.get(logFileEvent.getEventId());
                    logger.debug("Start Event, check if finished event exists for eventid={}", logFileEvent.getEventId());
                    if (finishedEvent != null) {
                        logger.debug("Finished event found for eventid={}, handle event and remove event from " +
                                "finished map", logFileEvent.getEventId());
                        handleLogFileEvent(logFileEvent, finishedEvent);
                        eventFinishedMap.remove(logFileEvent.getEventId());
                    } else {
                        logger.debug("Finished event not found for eventid={}, add in event started map",
                                logFileEvent.getEventId());
                        eventStartedMap.put(logFileEvent.getEventId(), logFileEvent);
                    }
                } else if (LogFileEventState.FINISHED.getEventState().equals(logFileEvent.getState().getEventState())) {
                    LogFileEvent startEvent = eventStartedMap.get(logFileEvent.getEventId());
                    logger.debug("Finished Event, check if started event exists for eventid={}", logFileEvent.getEventId());
                    if (startEvent != null) {
                        logger.debug("Started event found for eventid={}, handle event and remove event from " +
                                "started map", logFileEvent.getEventId());
                        handleLogFileEvent(startEvent, logFileEvent);
                        eventStartedMap.remove(logFileEvent.getEventId());
                    } else {
                        logger.debug("Started event not found for eventid={}, add in event finished map",
                                logFileEvent.getEventId());
                        eventFinishedMap.put(logFileEvent.getEventId(), logFileEvent);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Completed processing log file={}", logFilePath);
    }

    private void handleLogFileEvent(LogFileEvent logStartEvent, LogFileEvent logEndEvent) {
        long eventStartTime = logStartEvent.getTimestamp();
        long eventEndTime = logEndEvent.getTimestamp();
        eventHandlerService.handleLogFileEvent(logStartEvent, (eventEndTime - eventStartTime));
    }
}
