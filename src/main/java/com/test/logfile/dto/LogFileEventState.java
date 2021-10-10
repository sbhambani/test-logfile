package com.test.logfile.dto;

public enum LogFileEventState {

    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String eventState;

    private LogFileEventState(String eventState) {
        this.eventState = eventState;
    }

    public String getEventState() {
        return eventState;
    }
}
