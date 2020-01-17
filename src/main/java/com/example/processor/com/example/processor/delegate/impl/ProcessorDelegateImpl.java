/*
 @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.delegate.impl;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import com.example.processor.com.example.processor.delegete.ProcessorDelegate;
import com.example.processor.com.example.processor.model.ResponseModel;
import com.example.processor.com.example.processor.service.ProcessorService;
import com.example.processor.com.example.processor.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Delegate between controller and service.
 */
@Component
public class ProcessorDelegateImpl implements ProcessorDelegate {

    /**
     * {@link ProcessorService}
     */
    @Autowired
    private ProcessorService processorService;

    /**
     * {@link Utility}.
     */
    @Autowired
    private Utility utility;

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object addRecord(String inputData) {

        if (utility.isInvalidInputParameter(inputData)) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.INVALID_INPUT_PARAMETER)
                    .build();
        }
        return processorService.addRecord(inputData);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object deleteRecord(String recordId) {

        if (utility.isInvalidInputParameter(recordId)) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.INVALID_INPUT_PARAMETER)
                    .build();
        }

        return processorService.deleteRecord(recordId);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object deleteRecordsByKeyValue(String key, String value) {

        if (utility.isInvalidInputParameter(key) || utility.isInvalidInputParameter(value)) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.INVALID_INPUT_PARAMETER)
                    .build();
        }

        return processorService.deleteRecordsByKeyValue(key, value);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object getAllRecords() {
        return processorService.getAllRecords();
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object getRecordsByValue(String value) {

        if (utility.isInvalidInputParameter(value)) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.INVALID_INPUT_PARAMETER)
                    .build();
        }

        return processorService.getRecordsByValue(value);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object getRecordById(String recordId, Set<String> requiredResponseFields) {

        if (utility.isInvalidInputParameter(recordId)) {
            return ResponseModel
                    .builder()
                    .message(ProcessorConstants.INVALID_INPUT_PARAMETER)
                    .build();
        }

        return processorService.getRecordById(recordId, requiredResponseFields);
    }
}
