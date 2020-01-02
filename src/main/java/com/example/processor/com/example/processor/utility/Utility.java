/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.utility;

import com.example.processor.com.example.processor.constants.ProcessorConstants;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

/**
 * Utility services to write and read from database file.
 */
public class Utility {

    /**
     * The method create a temporary file that acts as cache.
     * @throws Exception
     */
    private  void createCacheFile() throws  Exception{
            // Copy contents from source to destination file.
            File source = new File(getClass().getClassLoader().getResource(ProcessorConstants.DATABASE_FILE_NAME).getFile());
            File dest = new File(getClass().getClassLoader().getResource(ProcessorConstants.TEMPORARY_FILE_NAME).getFile());
            if (!dest.exists())
                dest.createNewFile();
            FileUtils.copyFile(source, dest);
            FileWriter fileWriter = new FileWriter(dest, true);
            // Add special character to make Json objects inside json array.
            fileWriter.append("]");
            fileWriter.close();
    }

    /**
     * This method is used to read contents as Json Array from temporary cache file.
     * @return {@link JSONArray}
     * @throws Exception
     */
    public  JSONArray readDataFromCacheFile() throws  Exception{
           createCacheFile();
           JSONArray jsonArray = null;
           File dest = new File(getClass().getClassLoader().getResource(ProcessorConstants.TEMPORARY_FILE_NAME).getFile());
           // read from temporary cache file.
           JSONParser jsonParser = new JSONParser();
           FileReader reader = new FileReader(dest);
           Object obj = jsonParser.parse(reader);
           jsonArray = (JSONArray) obj;
           deleteContentFromCache();
           return jsonArray;
    }

    /**
     * This method is used to delete content from temporary file.
     */
    private void deleteContentFromCache() throws Exception{
           File temporaryFile = new File(getClass().getClassLoader().getResource(ProcessorConstants.TEMPORARY_FILE_NAME).getFile());
           PrintWriter writer = new PrintWriter(temporaryFile);
           writer.print("");
           writer.close();
    }

    /**
     * This method is used to write content to database file.
     * @param jsonObjects List of {@link JSONObject}
     * @throws Exception
     */
    public void writeToDatabase(List<JSONObject> jsonObjects) throws Exception{

            File source = new File(getClass().getClassLoader().getResource(ProcessorConstants.DATABASE_FILE_NAME).getFile());
            FileWriter fileWriter = new FileWriter(source);
            // Add special character to make Json objects inside json array.
            fileWriter.append("[");

            for (JSONObject each : jsonObjects) {
                fileWriter.append(each.toString());
            }
            fileWriter.close();
    }

    /**
     * Method to configure database before processing.
     */
    public void configDatabase() {
        try {
            File source = new File(getClass().getClassLoader().getResource(ProcessorConstants.DATABASE_FILE_NAME).getFile());
            FileWriter fileWriter = new FileWriter(source);
            // Add special character to make Json objects inside json array.
            fileWriter.append("[");
            fileWriter.close();
        } catch (Exception ex) {

        }
    }

    /**
     * This method is used to write a record.
     * @param inputJsonString input Json string data.
     * @return Added record as string.
     * @throws Exception
     */
    public String writeRecordToDatabase(String inputJsonString) throws Exception {
        String response;
        File file = new File(getClass().getClassLoader().getResource(ProcessorConstants.DATABASE_FILE_NAME).getFile());
        if (!file.exists())
            file.createNewFile();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(inputJsonString);
        // Create unique key for each record.
        jsonObject.put(ProcessorConstants.RECORD_UNIQUE_ID_KEY, UUID.randomUUID().toString());
        response = jsonObject.toString();
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
        return response;
    }

}
