package com.example.processor.com.example.processor.service;

import java.util.List;

public interface Processor {

    Object addRecord(String inputData);

    Object deleteRecord(String id);

    Object deleteRecordByKeyValue(String key, String value);

    List<Object> getAllRecords();

    List<Object> getSpecificRecords(List<String> fieldsList);
}
