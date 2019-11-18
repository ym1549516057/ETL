package com.example.demo.controller;

import com.example.demo.properties.Kettle;
import com.example.demo.utils.KettleUtil;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author YM
 * @date 2019/10/24 17:32
 */
public class Quartz implements Job {

    @Resource
    Kettle kettle;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String transName = jobDataMap.getString("transName");
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        KettleUtil.runResource(transName,kettle,request);

        System.out.println("test quartz excute....");
    }
}
