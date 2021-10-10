package com.test.logfile.processor;

import static org.assertj.core.api.Assertions.assertThat;

import com.test.logfile.entity.LogFileEventEntity;
import com.test.logfile.repository.LogFileEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {"spring.profiles.active=test"})
class LogFileProcessorTest {

    @Autowired
    private LogFileProcessor logFileProcessor;

    @Autowired
    private LogFileEventRepository eventRepository;

    @Test
    void testProcessLogFile() {
        // Arrange
        String testFilePath = getClass().getResource("/logfile/logfile.txt").getPath();

        // Act
        logFileProcessor.processLogFile(testFilePath);

        // Assert
        List<LogFileEventEntity> eventEntityList = eventRepository.findAll();
        assertThat(eventEntityList).isNotNull();
        assertThat(eventEntityList).isNotEmpty();
        assertThat(eventEntityList.size()).isEqualTo(3);
        assertLogFileEvents(eventEntityList);
    }

    private void assertLogFileEvents(List<LogFileEventEntity> eventEntityList) {
        for (LogFileEventEntity eventEntity : eventEntityList) {
            if ("scsmbstgra".equals(eventEntity.getEventId())) {
                assertThat(eventEntity.getEventId()).isEqualTo("scsmbstgra");
                assertThat(eventEntity.getAlert()).isFalse();
                assertThat(eventEntity.getDuration()).isEqualTo(2);
                assertThat(eventEntity.getType()).isEqualTo("APPLICATION_LOG");
                assertThat(eventEntity.getHost()).isEqualTo("1235");
            } else if ("scsmbstgrb".equals(eventEntity.getEventId())) {
                assertThat(eventEntity.getEventId()).isEqualTo("scsmbstgrb");
                assertThat(eventEntity.getAlert()).isTrue();
                assertThat(eventEntity.getDuration()).isEqualTo(7);
                assertThat(eventEntity.getType()).isNull();
                assertThat(eventEntity.getHost()).isNull();
            } else if ("scsmbstgrc".equals(eventEntity.getEventId())) {
                assertThat(eventEntity.getEventId()).isEqualTo("scsmbstgrc");
                assertThat(eventEntity.getAlert()).isTrue();
                assertThat(eventEntity.getDuration()).isEqualTo(12);
                assertThat(eventEntity.getType()).isNull();
                assertThat(eventEntity.getHost()).isNull();
            }
        }
    }
}
