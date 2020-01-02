/*
  Copyright 2019-2020
 */
package com.example.processor;

import com.example.processor.com.example.processor.utility.Utility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class
 */
@SpringBootApplication
public class ProcessorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

	/**
	 * Init setup of database storage file.
	 * @param arg0 String input
	 * @throws Exception
	 */
	@Override
	public void run(String... arg0) throws Exception {
		new Utility().configDatabase();
	}

}
