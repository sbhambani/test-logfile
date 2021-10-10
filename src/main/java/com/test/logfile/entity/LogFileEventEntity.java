package com.test.logfile.entity;

import javax.persistence.*;

@Entity
@Table(name = "LOGFILEEVENTS")
public class LogFileEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "duration", nullable = false)
    private Long duration;

    @Column(name = "alert", nullable = false)
    private Boolean alert;

    @Column(name = "type", nullable = true)
    private String type;

    @Column(name = "host", nullable = true)
    private String host;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Boolean getAlert() {
        return alert;
    }

    public void setAlert(Boolean alert) {
        this.alert = alert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "LogFileEventEntity{" +
                "id=" + id +
                ", eventId='" + eventId + '\'' +
                ", duration=" + duration +
                ", alert=" + alert +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
