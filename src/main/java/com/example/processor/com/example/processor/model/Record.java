/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.model;

import com.mongodb.BasicDBObject;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model class to store in database.
 * <p>
 *     Document named as record
 *     to store in database collection.
 * </p>
 * @author com.example
 */
@Document("record")
@Data
public class Record {

    /**
     * Unique id to distinguish the each record.
     * <p>
     *     Self generating hexadecimal id to uniquely
     *     distinguish each record.
     * </p>
     */
    @Id
    public ObjectId id;

    /**
     * To Store Input data as key value pair
     * <p>
     *     To store input data in form of key value
     *     form.
     * </p>
     */
    public BasicDBObject jsonObject;

}
