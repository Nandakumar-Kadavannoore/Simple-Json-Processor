/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.controller;

import com.example.processor.com.example.processor.delegate.impl.ProcessorDelegateImpl;
import com.example.processor.com.example.processor.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Processor Controller for Restful Web API's
 * <p>
 *     Main entry point of Restful
 *     web services.
 * </p>
 * @author com.example
 */
@RestController
public class ProcessorController {

    @Autowired
    private ProcessorDelegateImpl delegateImpl;

    /**
     * ProcessorController to create a new record.
     * <p>
     *     Used to create a new record.
     * </p>
     * @param inputData input data in the form of {@link String}
     * @return {@link ResponseEntity}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addRecord(@RequestBody String inputData) {
       ResponseModel responseModel = (ResponseModel) delegateImpl.addRecord(inputData);
       return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
    }

    /**
     * ProcessorController to delete record by key value pair.
     * <p>
     *     Used to delete record based on a key value.
     * </p>
     * @param key key value.
     * @param value value associated with key.
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "{key}/{value}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRecordsByKeyValue(
            @PathVariable(value = "key") @NotBlank String key,
            @PathVariable("value") @NotBlank String value) {
        ResponseModel responseModel = (ResponseModel) delegateImpl.deleteRecordsByKeyValue(key, value);
      return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * ProcessorController to get all records.
     *  <p>
     *    Used to get all records.
     *  </p>
     * @return {@link ResponseEntity}
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllRecords() {
        ResponseModel responseModel = (ResponseModel) delegateImpl.getAllRecords();
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * ProcessorController to delete a record by id.
     *  <p>
     *    Used to delete a record by id.
     *  </p>
     * @param recordId Specific unique id of the record.
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRecordById(@PathVariable(value = "id") @NotBlank String recordId) {
        ResponseModel responseModel = (ResponseModel) delegateImpl.deleteRecord(recordId);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * ProcessorController to get records by value.
     *  <p>
     *    Used to get records by value.
     *  </p>
     * @param value Specific unique id of the record.
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "/record/value/{value}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecordsByValue(@PathVariable(value = "value") @NotBlank  String value) {
        ResponseModel responseModel = (ResponseModel) delegateImpl.getRecordsByValue(value);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    /**
     * ProcessorController to get record by id.
     *  <p>
     *    Used to get record by record id. Optional
     *    parameter is required response fields. If
     *    required response fields is empty, response
     *    object contains all response fields else
     *    only those defined in fields given as response.
     *    If none matches in above, empty response is
     *    returned.
     *  </p>
     * @param recordId Specific unique id of the record.
     * @return {@link ResponseEntity}
     */
    @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
    public ResponseEntity<?> getRecordsById(
            @PathVariable(value = "recordId") @NotBlank  String recordId,
            @RequestParam(required = false ) Set< @NotBlank String> requiredResponseFields) {
        ResponseModel responseModel = (ResponseModel) delegateImpl.getRecordById(recordId, requiredResponseFields);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
