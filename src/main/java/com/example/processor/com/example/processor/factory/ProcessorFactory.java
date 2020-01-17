/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.factory;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import com.example.processor.com.example.processor.delegate.impl.ProcessorDelegateImpl;
import com.example.processor.com.example.processor.utility.Utility;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Processor factory.
 * <p>
 *     Factory to select appropriate operation to be performed
 *     based on input command consists of key, the operation type
 *     and value. The value inside value associated with key and
 *     another set of arguments.
 * </p>
 * @author com.example
 */
@NoArgsConstructor
@Component
public class ProcessorFactory {

    /**
     * {@link ProcessorDelegateImpl}.
     */
    @Autowired
    private ProcessorDelegateImpl processorDelegateImpl;

    /**
     * {@link Utility}.
     */
    @Autowired
    private Utility utility;

    /**
     * Method to select the operation to be performed.
     * @param condition operation type.
     * @param value  value to operated.
     * @return return information.
     */
    public  Object process(String condition, String value) {
        Object responseObject = null;
        switch (condition.toUpperCase())
        {
                 // Add record.
            case ProcessorConstants.ADD_RECORD :
                responseObject = processorDelegateImpl.addRecord(value);
                break;
                // Get all records.
            case ProcessorConstants.GET_ALL_RECORDS :
                responseObject = processorDelegateImpl.getAllRecords();
                break;
                // Get record by id.
            case  ProcessorConstants.GET_RECORD_BY_ID:
                // Get value and response fields.
                String[] fields= value.split(",");
                String[] responseFields = Arrays.copyOfRange(fields, 1, fields.length);
                Set<String> valueSet = new HashSet<>(Arrays.asList(responseFields));
                if (fields.length >= 1) {
                    responseObject = processorDelegateImpl.getRecordById(fields[0],
                            valueSet.isEmpty() ? null : valueSet);
                }
                 break;
                 // Delete record by id
            case ProcessorConstants.DELETE_RECORD_BY_ID:
                responseObject = processorDelegateImpl.deleteRecord(value);
                 break;
                 // Get record by value.
            case ProcessorConstants.GET_RECORD_BY_VALUE:
                responseObject = processorDelegateImpl.getRecordsByValue(value);
                 break;
                 // Delete record by key value
            case ProcessorConstants.DELETE_RECORD_BY_KEY_VALUE:
                String[] valueSplit = value.split(",");
                if (valueSplit.length == 2) {
                    responseObject = processorDelegateImpl.deleteRecordsByKeyValue(valueSplit[0], valueSplit[1]);
                }
                break;
                // To initialize database.
            case ProcessorConstants.INITIALIZE_DATABASE :
                utility.configDatabase();
                break;
        }
        return responseObject;
    }
}
