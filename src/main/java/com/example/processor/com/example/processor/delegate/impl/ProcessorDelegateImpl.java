/*
 @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.delegate.impl;

import com.example.processor.com.example.processor.delegete.ProcessorDelegate;
import com.example.processor.com.example.processor.service.ProcessorService;
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
     * {@link ProcessorDelegate}
     */
    @Override
    public Object addRecord(String inputData) {
        return processorService.addRecord(inputData);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object deleteRecord(String recordId) {
        return processorService.deleteRecord(recordId);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object deleteRecordsByKeyValue(String key, String value) {
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
        return processorService.getRecordsByValue(value);
    }

    /**
     * {@link ProcessorDelegate}
     */
    @Override
    public Object getRecordById(String recordId, Set<String> requiredResponseFields) {
        return processorService.getRecordById(recordId, requiredResponseFields);
    }
}
