package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * r_trans_log
 * @author 
 */
@Data
public class RTransLog implements Serializable {
    private Integer ID_BATCH;

    private String CHANNEL_ID;

    private String TRANSNAME;

    private String STATUS;

    private Long LINES_READ;

    private Long LINES_WRITTEN;

    private Long LINES_UPDATED;

    private Long LINES_INPUT;

    private Long LINES_OUTPUT;

    private Long LINES_REJECTED;

    private Long ERRORS;

    private Date STARTDATE;

    private Date ENDDATE;

    private Date LOGDATE;

    private Date DEPDATE;

    private Date REPLAYDATE;

    private String LOG_FIELD;

    private static final long serialVersionUID = 1L;

}