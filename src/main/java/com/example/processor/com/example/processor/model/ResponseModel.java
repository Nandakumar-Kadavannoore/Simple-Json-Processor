/*
  @Copyright 2019-2020
 */
package com.example.processor.com.example.processor.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * Model class for response.
 * <p>
 *     For all response, this model class is
 *     return.
 * </p>
 */
@Data
@Builder
@ToString
public class ResponseModel {

    /**
     * Store response message.
     */
    private String message;

    /**
     * Store response object.
     */
    private Object object;
}
