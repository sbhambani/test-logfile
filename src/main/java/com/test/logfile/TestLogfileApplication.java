package com.test.logfile;

import com.test.logfile.processor.LogFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TestLogfileApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(TestLogfileApplication.class);

	private final LogFileProcessor logFileProcessor;

	public TestLogfileApplication(LogFileProcessor logFileProcessor) {
		this.logFileProcessor = logFileProcessor;
	}

	public static void main(String[] args) {
		logger.info("Starting Logfile Test Application");
		SpringApplication.run(TestLogfileApplication.class, args);
		logger.info("Finished Logfile Test Application");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.debug("Received command line arguments={}", Arrays.toString(args));
		if (args != null && args.length > 0) {
			logFileProcessor.processLogFile(args[0]);
		}
	}
}
