/*
  @Copyright 2019-2020
 */
package com.example.processor;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import com.example.processor.com.example.processor.controller.ProcessorController;
import com.example.processor.com.example.processor.model.ResponseModel;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Integration Test cases for file based processor.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileProcessorApplicationTests {

	/**
	 * {@link ProcessorController}
	 */
	@Autowired
	private ProcessorController processorController;

	@Test
	@DisplayName("Test Add Record")
	@Order(1)
	void addRecord() throws Exception{
		ResponseEntity responseEntity = processorController.addRecord("{\n" +
				"\t \n" +
				"\"name\": \"Liam\",\n" +
				"    \"species\": \"cat\",\n" +
				"    \"breed\": \"tabby\"\n" +
				"\n" +
				"}");
		processorController.addRecord("{\n" +
				"\"season\" : \"winter\"\n" +
				"\n" +
				"}");
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Add Invalid Record")
	void addRecord_InvalidValues() throws Exception{
		ResponseEntity responseEntity = processorController.addRecord("{\n" +
				"\t \n" +
				"\"name\": \"Liam\",\n" +
				"   \"cat\",\n" +
				"    \"breed\": \"tabby\"\n" +
				"\n" +
				"}");
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.FAILURE_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Get All Records")
	@Order(2)
	void getAllRecords() {
		ResponseEntity responseEntity = processorController.getAllRecords();
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Get Records By Value")
	@Order(3)
	void getRecordsByValue() {
		ResponseEntity responseEntity = processorController.getRecordsByValue("cat");
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Delete Records By Key Value")
	@Order(4)
	void deleteRecordsByKeyValue() {
		ResponseEntity responseEntity = processorController.deleteRecordsByKeyValue("season", "winter");
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Delete Record By Id")
	@Order(5)
	void deleteRecordById() {
		ResponseEntity responseEntity = processorController.deleteRecordById("id");
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Get Record By Id")
	@Order(6)
	void getRecordById() {
		ResponseEntity responseEntity = processorController.getRecordsById("id", null);
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	@DisplayName("Test Get Record By Id And Having Required fields")
	@Order(7)
	void getRecordByIdAndOfRequiredResponse() {
		Set<String> requiredResponseFields = Stream.of("_id").collect(Collectors.toSet());
		ResponseEntity responseEntity = processorController.getRecordsById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", requiredResponseFields);
		ResponseModel responseModel = (ResponseModel) responseEntity.getBody();
		Assert.assertEquals(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
		Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
