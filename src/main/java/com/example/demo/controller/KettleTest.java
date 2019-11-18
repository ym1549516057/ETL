package com.example.demo.controller;

import com.example.demo.entity.RTransLog;
import com.example.demo.properties.Kettle;
import com.example.demo.utils.KettleUtil;
import com.example.demo.service.RtransService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.pentaho.di.trans.TransMeta;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author YM
 * @date 2019/10/22 10:25
 */
@RestController
@RequestMapping("kettle")
public class KettleTest {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    Logger logger = LoggerFactory.getLogger(KettleTest.class);

    @Autowired
    RtransService rtransService;

    @Resource
    Kettle kettle;

    /**
     * 执行转换
     *
     * @param params
     * @param ktrpath
     */
    @GetMapping("/run")
    public void runTransfer(String[] params, String ktrpath) {
        KettleUtil.runTransfer(params, ktrpath);
    }

    /**
     * 执行资源库转换
     */
    @GetMapping("runResource")
    public String runResource(String transName, HttpServletRequest request) {
        return KettleUtil.runResource(transName, kettle,request);
    }

    /**
     * 远程执行转换
     *
     * @param transName
     * @return
     */
    @GetMapping("remoteRunTrans")
    public String remoteRunTrans(String transName) {
        return KettleUtil.remoteRunTrans(transName, kettle);
    }

    /**
     * 集群运行
     *
     * @param transName
     * @return
     */
    @GetMapping("runClusterTrans")
    public Map<TransMeta, String> runClusterTrans(String transName) {
        return KettleUtil.runClusterTrans(transName, kettle);
    }

    /**
     * 执行资源库作业
     */
    @GetMapping("runJob")
    public void runJob(String jobName) {
        KettleUtil.runJob(jobName, kettle);
    }

    @GetMapping("remoteRunJob")
    public void remoteRunJob(String jobName) {
        KettleUtil.remoteRunJob(jobName, kettle);
    }

    @GetMapping("getJob")
    public List<String> getJob() {
        return KettleUtil.getJob(kettle);
    }

    @GetMapping("test")
    public void testJob(String jobName) {
//        KettleUtil.testJob(jobName);
        logger.info("test");
    }


    @GetMapping("excuteQuartz")
    public void quartzExcute() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            String jobName = "trans1";

            JobDetail job = JobBuilder.newJob(Quartz.class)
                    .withIdentity("test", "groupOne")
                    .usingJobData("transName", jobName)
                    .build();

            String cron = "0 0/2 14 * * ?";
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "groupOne")
                    .startNow()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(120).repeatForever())
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("runResource1")
    public void runResource1(String transName){
        com.example.demo.utils.KettleTest.runResource1(transName,kettle);
    }

    @GetMapping("runResource2")
    public void runResource2(String transName){
        com.example.demo.utils.KettleTest.runResource2(transName);
    }

    @GetMapping("getByPage")
    public PageInfo<RTransLog> getByPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<RTransLog> transLogList = rtransService.getAll();
        PageInfo<RTransLog> pageInfo = new PageInfo<>(transLogList);
        return pageInfo;
    }
}
