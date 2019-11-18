package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * r_step_log
 * @author 
 */
public class RStepLog implements Serializable {
    private Integer idBatch;

    private String channelId;

    private Date logDate;

    private String transname;

    private String stepname;

    private Integer stepCopy;

    private Long linesRead;

    private Long linesWritten;

    private Long linesUpdated;

    private Long linesInput;

    private Long linesOutput;

    private Long linesRejected;

    private Long errors;

    private static final long serialVersionUID = 1L;

}