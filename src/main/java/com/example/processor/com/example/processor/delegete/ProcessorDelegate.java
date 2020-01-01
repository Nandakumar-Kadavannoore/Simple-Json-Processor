/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.delegete;

import java.util.Set;

/**
 * Base Delegate Interface.
 * @author com.example
 */
public interface ProcessorDelegate {

    /**
     * Used to add a new record.
     * @param inputData input json object in form of string.
     * @return {@link Object}
     */
    Object addRecord(String inputData);

    /**
     * Used to delete a new record.
     * @param recordId Unique id to distinguish each record.
     * @return {@link Object}
     */
    Object deleteRecord(String recordId);

    /**
     * Used to delete records by key value pair.
     * <p>
     *     Find those records that match with given key value
     *     and delete those records from collection.
     * </p>
     * @param key Specific key string of field in Json object.
     * @param value Value associated with key.
     * @return {@link Object}
     */
    Object deleteRecordsByKeyValue(String key, String value);

    /**
     * Used to return all records that stored so far.
     * @return {@link Object}
     */
    Object getAllRecords();

    /**
     * Used to get specific records consists given value.
     * <p>
     *     Will search all records and return those records
     *     having value for any key in json document.
     * </p>
     * @param value specific value to search.
     * @return {@link Object}
     */
    Object getRecordsByValue(String value);

    /**
     * Used to get record by id and return fields response.
     *  <p>
     *      Get the record that match with record id and
     *      response field key consists of only input key field.
     *      If empty requiredResponseFields as input, then return all
     *      key fields as response.
     *  </p>
     * @param recordId unique id of the record or json object.
     * @param requiredResponseFields list of fields that should return as
     *                               response. fields must be equal to key.
     * @return {@link Object}
     */
    Object getRecordById(String recordId, Set<String> requiredResponseFields);
}
