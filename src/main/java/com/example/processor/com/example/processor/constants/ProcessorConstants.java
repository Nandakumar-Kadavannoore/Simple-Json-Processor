/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.constants;

/**
 * Constants used in this application.
 * @author com.example
 */
public  class ProcessorConstants {

    private ProcessorConstants() {
      // To prevent object creation.
    }

    /**
     * Constant to store Success response message.
     */
    public static final String SUCCESS_RESPONSE_MESSAGE = "Success";

    /**
     * Constant to store Failure response message.
     */
    public static final String FAILURE_RESPONSE_MESSAGE = "Failure";

    /**
     * Constant to store record unique id key.
     */
    public static final String RECORD_UNIQUE_ID_KEY = "_id";

    /**
     * Constant to store database file name.
     */
    public static final String DATABASE_FILE_NAME = "database";

    /**
     * Constant to store temporary file name.
     */
    public static final String TEMPORARY_FILE_NAME = "temporary.storage";

    /**
     * Constant to store record add key.
     */
    public static final String ADD_RECORD = "ADD";

    /**
     * Constant to store Get all records key.
     */
    public static final String GET_ALL_RECORDS = "GET_ALL";

    /**
     * Constant to store Get record by id key.
     */
    public static final String GET_RECORD_BY_ID = "GET_BY_ID";

    /**
     * Constant to store Delete record by id key.
     */
    public static final String DELETE_RECORD_BY_ID = "DELETE_BY_ID";

    /**
     * Constant to store Get Record By value key.
     */
    public static final String GET_RECORD_BY_VALUE = "GET_BY_VALUE";

    /**
     * Constant to store Delete record by key value key.
     */
    public static final String DELETE_RECORD_BY_KEY_VALUE = "DELETE_RECORD_BY_KEY_VALUE";

    /**
     * Constant to store Initialize database key.
     */
    public static final String INITIALIZE_DATABASE = "INITIALIZE";

    /**
     * Constant to store invalid input parameter response message.
     */
    public static final String INVALID_INPUT_PARAMETER = "Invalid Input parameter";
}
