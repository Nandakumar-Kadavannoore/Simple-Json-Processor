/*
 @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.service.impl;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import com.example.processor.com.example.processor.model.ResponseModel;
import com.example.processor.com.example.processor.service.ProcessorService;
import com.example.processor.com.example.processor.utility.Utility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for storing json data in file.
 * <p>
 *     This service class store input data as
 *     json in a file.
 * </p>
 * @author com.example
 */
@Service
public class FileProcessorServiceImpl implements ProcessorService {

    /**
     * {@link Utility}.
     */
    @Autowired
    private Utility utility;

    /**
     * {@link ProcessorService}
     *
     * @param inputJsonString input json object in form of string.
     * @return {@link ResponseModel}
     */
    @Override
    public Object addRecord(String inputJsonString) {

        String response;
        try {
            response = utility.writeRecordToDatabase(inputJsonString, false);
        } catch (Exception exception) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.FAILURE_RESPONSE_MESSAGE)
                    .build();
        }
        // return created record as response.
        return ResponseModel
                 .builder()
                 .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                 .object(response)
                 .build();
    }

    /**
     * {@link ProcessorService}
     *
     * @param recordId Unique id to distinguish each record.
     * @return {@link ResponseModel}
     */
    @Override
    public Object deleteRecord(String recordId) {
        return deleteByKeyValue(ProcessorConstants.RECORD_UNIQUE_ID_KEY, recordId);
    }

    /**
     * Used to delete records having given key value fields.
     * {@link ProcessorService}
     *
     * @param key   Specific field key of json object.
     * @param value Specific field key value with above key.
     * @return {@link ResponseModel}
     */
    public ResponseModel deleteByKeyValue(String key, String value) {
        try {
            JSONArray jsonArray = utility.readDataFromCacheFile();
            deleteRecordsFromDatabase(jsonArray, key, value);
        } catch (Exception ex) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.FAILURE_RESPONSE_MESSAGE)
                    .build();
        }

        // return response.
        return ResponseModel
                        .builder()
                        .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                        .build();
    }

    /**
     * Used to delete records having given key value from database.
     * <p>
     *    Check if any Json object with given key value exist or not,
     *    if not exist add to list of json objects, update with new
     *    list in database.
     * </p>
     * @param jsonArray data in the current database (file).
     * @param key Specific key to check.
     * @param value Specific value associated with key.
     * @throws Exception exception when parsing the Json.
     */
    private void deleteRecordsFromDatabase(JSONArray jsonArray, String key, String value) throws Exception {
        // Check if input JSONArray null or empty.
        if (Optional.ofNullable(jsonArray).isPresent()) {
            List<JSONObject> jsonObjects = new ArrayList<>();
            // iterate through each JSONObject records.
            jsonArray.forEach(eachJsonObject -> {
                JSONObject jsonObject = (JSONObject) eachJsonObject;
                // check if given key value exist in each JsonObject.
                if (jsonObject.containsKey(key) && jsonObject.get(key).toString().equalsIgnoreCase(value)) {
                    // if exist, not add to list of Json objects.
                } else {
                    jsonObjects.add(jsonObject);
                }
            });
            // Utility method to save records back to database file.
            utility.writeToDatabase(jsonObjects);
        }
    }

    /**
     * Used to get all records.
     * @return {@link ResponseModel}
     */
    public ResponseModel getRecordsInDatabase() {

        List<JSONObject> jsonObjects = new ArrayList<>();
        try {
            JSONArray jsonArray = utility.readDataFromCacheFile();
            if (Optional.ofNullable(jsonArray).isPresent()) {
                // Iterate through each json objects and to response list.
                jsonArray.forEach( eachJSONObject -> {
                    JSONObject jsonObject = (JSONObject) eachJSONObject;
                    jsonObjects.add(jsonObject);
                });
            }

        } catch (Exception ex) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.FAILURE_RESPONSE_MESSAGE)
                    .build();
        }
        return ResponseModel
                        .builder()
                        .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                        .object(jsonObjects)
                        .build();
    }

    /**
     * {@link ProcessorService}
     * @param key Specific key string.
     * @param value Value associated with key.
     * @return {@link ResponseModel}
     */
    @Override
    public Object deleteRecordsByKeyValue(String key, String value) {
        return deleteByKeyValue(key, value);
    }

    /**
     * {@link ProcessorService}
     * @return {@link ResponseModel}
     */
    @Override
    public Object getAllRecords() {
        return getRecordsInDatabase();
    }

    /**
     * {@link ProcessorService}
     * @param recordId unique id of the record.
     * @param requiredResponseFields list of fields that should return as
     *                  response. fields must be equal to key.
     * @return {@link ResponseModel}
     */
    @Override
    public Object getRecordById(String recordId, Set<String> requiredResponseFields) {
        // If recordId is empty get all records.
        if (!Optional.ofNullable(recordId).isPresent()) {
            return getRecordsInDatabase();
        }
        return getRecordInDatabaseById(recordId, requiredResponseFields);
    }

    /**
     * Get record by id from database.
     * @param recordId unique id of record.
     * @param requiredResponseFields required response fields.
     * @return {@link ResponseModel}
     */
    private ResponseModel getRecordInDatabaseById(String recordId, Set<String> requiredResponseFields) {
        JSONObject requiredJsonObject = new JSONObject();
        try {
            // Read data from database.
            JSONArray jsonArray = utility.readDataFromCacheFile();
            for (Object eachJSONObject: jsonArray) {
                JSONObject jsonObject = (JSONObject) eachJSONObject;
                // Check if each Json Object matches with given id if yes assign to response and exist loop.
                if (jsonObject.containsKey(ProcessorConstants.RECORD_UNIQUE_ID_KEY) &&
                        jsonObject.get(ProcessorConstants.RECORD_UNIQUE_ID_KEY).toString()
                                .equalsIgnoreCase(recordId)) {
                    requiredJsonObject =  jsonObject;
                }
            }

        } catch (Exception ex) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.FAILURE_RESPONSE_MESSAGE)
                    .build();
        }
        return (requiredResponseFields == null || requiredResponseFields.isEmpty()) ?
                ResponseModel
                .builder()
                .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                .object(requiredJsonObject)
                .build() : getResponseObjectWithRequiredFields(requiredJsonObject, requiredResponseFields);
    }

    /**
     * Method to get response json object consists of required fields.
     * @param requiredJsonObject json object for searching the fields.
     * @param requiredResponseFields response fields.
     * @return {@link ResponseModel}
     */
    private ResponseModel getResponseObjectWithRequiredFields(
            JSONObject requiredJsonObject, Set<String> requiredResponseFields) {

            JSONObject responseJsonObject;
            Set<String> keysInEachJSONObject = requiredJsonObject.keySet();
            JSONObject  jsonObject = new JSONObject();
            /* Compare response fields with Json Object keys if
               if none matches, not add json object to response.
             */
            keysInEachJSONObject.retainAll(requiredResponseFields);
            if (!keysInEachJSONObject.isEmpty()) {
                keysInEachJSONObject.stream().forEach( eachKey -> {
                    String value = requiredJsonObject.get(eachKey).toString();
                    jsonObject.put(eachKey, value);
                });
            }
            responseJsonObject = jsonObject;

        return ResponseModel
                .builder()
                .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                .object(responseJsonObject)
                .build();
    }

    /**
     * {@link ProcessorService}
     * @param value specific value.
     * @return {@link ResponseModel}
     */
    @Override
    public Object getRecordsByValue(String value) {

        List<JSONObject> responseJSONObjects = new ArrayList<>();
        try {
            // Get all records from database.
            JSONArray jsonArray =  utility.readDataFromCacheFile();
            if (Optional.ofNullable(jsonArray).isPresent()) {
                // Iterate through each Json object to check if requested value exist or not.
                jsonArray.stream().forEach( eachJSONObject -> {
                    JSONObject jsonObject = (JSONObject) eachJSONObject;
                    Set<String> keys = jsonObject.keySet();
                    keys.stream().forEach( eachKey -> {
                                if (jsonObject.get(eachKey).toString().equals(value)) {
                                    // If record of given value exist, add to response list.
                                    responseJSONObjects.add(jsonObject);
                                }
                            });
                });
            }
        }
        catch (Exception ex) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.FAILURE_RESPONSE_MESSAGE)
                    .build();
        }
        return ResponseModel
                        .builder()
                        .message(ProcessorConstants.SUCCESS_RESPONSE_MESSAGE)
                        .object(responseJSONObjects)
                        .build();
    }
}
