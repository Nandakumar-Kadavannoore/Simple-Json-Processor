/*
  Copyright 2019-2020
 */
package com.example.processor;

import com.example.processor.com.example.processor.factory.ProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * Main application class
 */
@SpringBootApplication
public class ProcessorApplication extends SpringBootServletInitializer {

	/**
	 * Logger to log application input request parameters.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProcessorApplication.class);

	/**
	 * Main class
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Get bean of ProcessFactory.
		ConfigurableApplicationContext appContext = SpringApplication.run(ProcessorApplication.class, args);
		ProcessorFactory processorFactory = appContext.getBean(ProcessorFactory.class);
		// Extract key from arguments.
		String values = Arrays.toString(args);
		if (values != null || !values.isEmpty()) {
			values = values.substring(1, values.length() - 1);
			// key and value separated by delimiter.
			String[] keyValueArray = values.split("=");

			// call factory to select specific service.
			//  if value is empty pass null.
			Object object = processorFactory.process(keyValueArray[0], keyValueArray.length > 1 ? keyValueArray[1] : null);
			logger.info("---------Result Obtained-----------");
			logger.info(object.toString());
		}
	}

}
