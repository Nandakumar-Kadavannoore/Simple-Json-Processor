/*
 @Copyright 2019-2020
 */
package com.example.processor.service;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import com.example.processor.com.example.processor.model.ResponseModel;
import com.example.processor.com.example.processor.service.impl.FileProcessorServiceImpl;
import com.example.processor.com.example.processor.utility.Utility;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service test of file processing service.
 * <p>
 *     Service test of file processing service.
 *     Pattern of test case name is --operation_condition_finalStatus--.
 *     example: getRecordById_WhenRecordExist_Success.
 * </p>
 * @author com.example
 */
@SpringBootTest
public class FileProcessorServiceTests {

    /**
     * {@link FileProcessorServiceImpl}
     */
    @InjectMocks
    private FileProcessorServiceImpl fileProcessorServiceImpl;

    /**
     * {@link Utility}.
     */
    @Mock
    private Utility utility;

    /**
     * Setup of mock.
     */
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Unit Test to check if record is inserted if correct json string data as input.
     */
    @Test
    public void addRecord_WhenCorrectInput_Success() throws Exception{

        String inputJsonString = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "\n" +
                "}";

        String outputJsonString = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"1234\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel)
                   fileProcessorServiceImpl.addRecord(inputJsonString);

        Mockito.when(utility.writeRecordToDatabase(inputJsonString, true)).thenReturn(outputJsonString);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());

    }

    /**
     * Unit Test to delete record by id when requested record exist in database.
     */
    @Test
    @DisplayName("Delete Record by Id if requested record exist in database")
    public void deleteRecordById_WhenRequestedRecordExist_Success() throws Exception{

        String outputJsonStringResponseOne = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"1234\",\n" +
                "\n" +
                "}";

        String outputJsonStringResponseTwo = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"12334\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("1234");

        JSONArray jsonArrayResponse = new JSONArray();
        jsonArrayResponse.add(outputJsonStringResponseOne);
        jsonArrayResponse.add(outputJsonStringResponseTwo);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(jsonArrayResponse);

        Mockito.when(utility.writeRecordToDatabase(outputJsonStringResponseTwo, false))
                .thenReturn(outputJsonStringResponseTwo);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id  When requested record not exist in database.
     */
    @Test
    @DisplayName("Delete Record by Id if requested record doesn't exist in database")
    public void deleteRecordById_WhenRequestedRecordNotExist_Success() throws Exception{

        String outputJsonStringResponseOne = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"1234\",\n" +
                "\n" +
                "}";

        String outputJsonStringResponseTwo = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"12334\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("11124");

        JSONArray jsonArrayResponse = new JSONArray();
        jsonArrayResponse.add(outputJsonStringResponseOne);
        jsonArrayResponse.add(outputJsonStringResponseTwo);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(jsonArrayResponse);

        Mockito.when(utility.writeRecordToDatabase(outputJsonStringResponseTwo, false))
                .thenReturn(outputJsonStringResponseTwo);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id  When read database return null
     */
    @Test
    @DisplayName("Delete Record by Id if requested record exist in database and read operation return null")
    public void deleteRecordById_WhenRequestedRecordExist_AndReadDBReturnNull_Success() throws Exception{

        String outputJsonStringResponseTwo = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"12334\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("11124");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Mockito.when(utility.writeRecordToDatabase(outputJsonStringResponseTwo, false))
                .thenReturn(outputJsonStringResponseTwo);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id  When read database return empty.
     */
    @Test
    @DisplayName("Delete Record by Id if requested record exist in database and read operation return empty")
    public void deleteRecordById_WhenRequestedRecordExist_AndReadDBReturnEmpty_Success() throws Exception{

        String outputJsonStringResponseTwo = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"12334\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("11124");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Mockito.when(utility.writeRecordToDatabase(outputJsonStringResponseTwo, false))
                .thenReturn(outputJsonStringResponseTwo);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id When requested only the requested record exist in database.
     */
    @Test
    @DisplayName("Delete Record by Id if only the requested record exist in database")
    public void deleteRecordById_WhenRequestOneExist_AndOnlyRecordInDB_Success() throws Exception{

        String outputJsonStringResponseOne = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"1234\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("1234");

        JSONArray jsonArrayResponse = new JSONArray();
        jsonArrayResponse.add(outputJsonStringResponseOne);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(jsonArrayResponse);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id when no records exist.
     */
    @Test
    @DisplayName("Delete Record by Id when get records return null")
    public void deleteRecordById_WhenGetRecords_NullResponseSuccess() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("1234");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by id when no records exist.
     */
    @Test
    @DisplayName("Delete Record by Id when get records return empty response")
    public void deleteRecordById_WhenGetRecords_EmptyResponseSuccess() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecord("1234");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to delete record by key value.
     */
    @Test
    public void deleteRecordByKeyValue_WhenRecordExist_Success() {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.deleteRecordsByKeyValue("season", "winter");

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get all records.
     */
    @Test
    public void getAllRecords_WhenRecordsExist_Success() {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getAllRecords();

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get all records, get all records method return null.
     */
    @Test
    public void getAllRecords_WhenDBReturnNUll_Success() throws Exception {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getAllRecords();

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get all records, get all records method return empty response.
     */
    @Test
    public void getAllRecords_WhenDBReturnEmpty_Success() throws Exception {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getAllRecords();

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get all records, return empty response.
     */
    @Test
    @DisplayName("Test get all records return empty list as response")
    public void getAllRecords_WhenDBREadReturnEmpty_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getAllRecords();

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get all records return null response.
     */
    @Test
    @DisplayName("Test get all records return empty list as response")
    public void getAllRecords_WhenDBReturnReturnNull_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getAllRecords();

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record by id.
     */
    @Test
    public void getRecordById_WhenRequestedRecordExist_Success() {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record by id.
     */
    @Test
    public void getRecordById_WhenDBReadReturnNull_Success() throws Exception {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record by id.
     */
    @Test
    public void getRecordById_WhenDBReadReturnEmpty_Success() throws Exception {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get records by value.
     */
    @Test
    public void getRecordsByValue_WhenRequestedOneExist_Success() {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getRecordsByValue("cat");

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get records by value.
     */
    @Test
    public void getRecordsByValue_WhenReadDBIsNull_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getRecordsByValue("cat");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get records by value, when response is empty.
     */
    @Test
    public void getRecordsByValue_WhenReadDBIsEmpty_Success() throws Exception {

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.getRecordsByValue("cat");

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record of given id and required response.
     */
    @Test
    public void getRecordByIdAndOfRequiredResponse_WhenRequestedIdExist_Success() {

        Set<String> requiredResponseFields = Stream.of("_id").collect(Collectors.toSet());
        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", requiredResponseFields);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record of given id and with out required response.
     */
    @Test
    public void getRecordByIdAndWithoutRequiredResponse_WhenRequestedIdExist_Success() throws Exception{

        String outputJsonStringResponseOne = "{\n" +
                "\t \n" +
                "\"name\": \"Liam\",\n" +
                "    \"species\": \"cat\",\n" +
                "    \"breed\": \"tabby\"\n" +
                "    \"id\": \"1234\",\n" +
                "\n" +
                "}";

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        JSONArray jsonArrayResponse = new JSONArray();
        jsonArrayResponse.add(outputJsonStringResponseOne);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(jsonArrayResponse);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record of given id and with out required response, when response is empty.
     */
    @Test
    public void getRecordByIdAndWithoutRequiredResponse_WhenReadDBReturnEmptyResponse_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record of given id and with out required response, when response is null.
     */
    @Test
    public void getRecordByIdAndWithoutRequiredResponse_WhenReadDBReturnNullResponse_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }


    /**
     * Unit Test to get record of given id and with out required response, when no records.
     */
    @Test
    public void getRecordByIdAndRequiredResponse_WhenReadDBReturnEmptyResponse_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(new JSONArray());

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

    /**
     * Unit Test to get record of given id and with out required response, when no records.
     */
    @Test
    public void getRecordByIdAndRequiredResponse_WhenReadDBReturnNullResponse_Success() throws Exception{

        ResponseModel responseModel = (ResponseModel) fileProcessorServiceImpl.
                getRecordById("ffe7ada6-7e0f-4144-a69a-d9cfe512e21a", null);

        Mockito.when(utility.readDataFromCacheFile()).thenReturn(null);

        Assert.assertSame(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE, responseModel.getMessage());
    }

}
