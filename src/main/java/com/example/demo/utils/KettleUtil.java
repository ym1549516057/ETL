package com.example.demo.utils;

import com.example.demo.properties.Kettle;
import org.apache.http.HttpRequest;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.*;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryElementMetaInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.cluster.TransSplitter;
import org.pentaho.metastore.api.IMetaStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author YM
 * @date 2019/10/25 9:56
 */
public class KettleUtil {

    /**
     * 执行资源库转换并记录日志
     *
     * @param transName
     * @param kettle
     */
    public static String runResource(String transName, Kettle kettle, HttpServletRequest request) {
        try {
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            ProgressMonitorListener monitorListener = new ProgressNullMonitorListener();
            //选择转换
            TransMeta transMeta = repository.loadTransformation(transName, repositoryDirectoryInterface, monitorListener, true, null);
            transMeta.setLogLevel(LogLevel.ROWLEVEL);

            //记录日志
//            VariableSpace space = new Variables();
//            space.setVariable("resource", "resource");
//            space.initializeVariablesFrom(null);
//            TransLogTable transLogTable = TransLogTable.getDefault(space, transMeta, transMeta.getSteps());
//            transLogTable.setConnectionName("resource");
//            transLogTable.setSchemaName("kettle-reource");
//            transLogTable.setTableName("r_trans_log");
//            transMeta.setTransLogTable(transLogTable);
//
//            StepLogTable stepLogTable = StepLogTable.getDefault(space,transMeta);
//            stepLogTable.setConnectionName("resource");
//            stepLogTable.setSchemaName("kettle-reource");
//            stepLogTable.setTableName("r_trans_step_log");
//            transMeta.setStepLogTable(stepLogTable);
//
//
            Trans trans = new Trans(transMeta);
            trans.getLogChannelId();

            String subject = "自定义日志";
            LogChannelFactory logChannelFactory = new LogChannelFactory();
            LogChannel logChannel = logChannelFactory.create(subject);
            logChannel.logMinimal("测试日志");
            String channelId = logChannel.getLogChannelId();
            String cantaionerId = logChannel.getContainerObjectId();
            System.out.println(cantaionerId);

            //设置参数
            trans.setVariable("table_name","cow_info");

//            String[] listVariables = trans.listVariables();
            List<String> usedVariables = transMeta.getUsedVariables();


            trans.execute(null);
            List<LoggingHierarchy> loggingHierarchies = trans.getLoggingHierarchy();

//            trans.wait();
            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                throw new Exception("转换时发生错误");
            }

            List<LoggingHierarchy> loggingHierarchy = trans.getLoggingHierarchy();
            return trans.getResult().getLogText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 远程执行转换
     *
     * @param transName
     * @param kettle
     * @return
     */
    public static String remoteRunTrans(String transName, Kettle kettle) {
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            ProgressMonitorListener monitorListener = new ProgressNullMonitorListener();

            //选择转换
            TransMeta transMeta = repository.loadTransformation(transName, repositoryDirectoryInterface, monitorListener, true, null);
            TransExecutionConfiguration transExecutionConfiguration = new TransExecutionConfiguration();
            transExecutionConfiguration.setExecutingLocally(false);
            transExecutionConfiguration.setExecutingClustered(false);
            transExecutionConfiguration.setExecutingRemotely(true);

            List<SlaveServer> slaveServers = repository.getSlaveServers();
            IMetaStore iMetaStore = repository.getMetaStore();

            transExecutionConfiguration.setRemoteServer(slaveServers.get(0));
            String s = Trans.sendToSlaveServer(transMeta, transExecutionConfiguration, repository, iMetaStore);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 集群执行资源库转换
     *
     * @param transName
     * @param kettle
     * @return
     */
    public static Map<TransMeta, String> runClusterTrans(String transName, Kettle kettle) {
        try {
            KettleEnvironment.init();        //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            ProgressMonitorListener monitorListener = new ProgressNullMonitorListener();
            TransMeta transMeta = repository.loadTransformation(transName, repositoryDirectoryInterface, monitorListener, true, null);

            //设置执行模式
            TransExecutionConfiguration configuration = new TransExecutionConfiguration();
            //设置集群为true
            configuration.setExecutingClustered(true);
            //设置local和远程为false
            configuration.setExecutingLocally(false);
            configuration.setExecutingRemotely(false);
            configuration.setClusterPosting(true);
            //设置准备执行集群
            configuration.setClusterPreparing(true);
            //设置开始执行
            configuration.setClusterStarting(true);

            LogChannelInterface logChannelInterface = transMeta.getLogChannel();
            TransSplitter transSplitter = Trans.executeClustered(transMeta, configuration);
            return transSplitter.getCarteObjectMap();

        } catch (KettleException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 执行资源库作业
     *
     * @param jobName
     * @param kettle
     */
    public static void runJob(String jobName, Kettle kettle) {
        try {
            //初始化kettle环境
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);

            JobMeta jobMeta = repository.loadJob(jobName, repositoryDirectoryInterface, null, null);
//            JobLogTable jobLogTable = JobLogTable.getDefault(jobMeta,jobMeta);
            VariableSpace space = new Variables();
            space.setVariable("resource", "resource");
            space.initializeVariablesFrom(null);
            JobLogTable jobLogTable = JobLogTable.getDefault(space, jobMeta);
            jobLogTable.setConnectionName("resource");
            jobLogTable.setSchemaName("resource");
            jobLogTable.setTableName("r_job_log");
            jobMeta.setJobLogTable(jobLogTable);


            Job job = new Job(repository, jobMeta);
            job.setVariable("table_name","cow_info");
            job.start();
            job.waitUntilFinished();
            if (job.getErrors() > 0) {
                throw new Exception("执行作业发生异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void remoteRunJob(String jobName, Kettle kettle) {
        try {
            //初始化kettle环境
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);

            JobMeta jobMeta = repository.loadJob(jobName, repositoryDirectoryInterface, null, null);
            //添加日志表
            VariableSpace space = new Variables();
            space.setVariable("resource", "resource");
            space.initializeVariablesFrom(null);
            JobLogTable jobLogTable = JobLogTable.getDefault(space, jobMeta);
            jobLogTable.setConnectionName("resource");
            jobLogTable.setSchemaName("resource");
            jobLogTable.setTableName("r_job_log");
            jobMeta.setJobLogTable(jobLogTable);
            JobExecutionConfiguration jobExecutionConfiguration = new JobExecutionConfiguration();
            jobExecutionConfiguration.setExecutingLocally(false);
            jobExecutionConfiguration.setExecutingRemotely(true);

            //获取服务器
            List<SlaveServer> slaveServers = repository.getSlaveServers();
            jobExecutionConfiguration.setRemoteServer(slaveServers.get(0));

            IMetaStore iMetaStore = repository.getMetaStore();

            Job.sendToSlaveServer(jobMeta, jobExecutionConfiguration, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runTransfer(String[] params, String ktrpath) {
        Trans trans = null;
        try {
            //初始化kettle环境
            KettleEnvironment.init();
            EnvUtil.environmentInit();

            //新建转换
            TransMeta transMeta = new TransMeta(ktrpath);
            trans = new Trans(transMeta);
            //执行转换
            trans.execute(params);

            //等待转换执行完成
            trans.waitUntilFinished();
            if (trans.getErrors() > 0) {
                throw new Exception("转换发生异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getJob(Kettle kettle) {
        //初始化kettle环境
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            assert repositoryDirectoryInterface != null;
            ObjectId[] objectId = repositoryDirectoryInterface.getDirectoryIDs();
            //获取作业和转换
            List<RepositoryElementMetaInterface> jobAndTransformationObjects = repository.getJobAndTransformationObjects(new LongObjectId(0), false);

            return null;
        } catch (KettleException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getLog(Kettle kettle) {
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            LogChannelInterface log = repository.getLog();
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }


    public static void testJob(String jobName, Kettle kettle) {
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = create(repository, kettle);
            List<RepositoryElementMetaInterface> jobAndTransformationObjects = repository.getJobAndTransformationObjects(new LongObjectId(0), false);

            JobMeta jobMeta = repository.loadJob(jobName, repositoryDirectoryInterface, null, null);
            Job job = new Job(repository, jobMeta);
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }


    /**
     * 连接到资源库
     *
     * @param repository
     * @return
     */
    private static RepositoryDirectoryInterface create(KettleDatabaseRepository repository, Kettle kettle) {
        try {
            KettleEnvironment.init();
            DatabaseMeta databaseMeta = new DatabaseMeta(kettle.getResourceName(), kettle.getType(), kettle.getAccess(), kettle.getHost(), kettle.getDbName(), kettle.getPort(), kettle.getDbUser(), kettle.getDbPassword());
            //选择资源库
            KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta("kettle", "resource", "描述", databaseMeta);
            repository.init(kettleDatabaseRepositoryMeta);
            //连接资源库
            repository.connect(kettle.getResourceUsername(), kettle.getResourcePassword());
            return repository.loadRepositoryDirectoryTree();
        } catch (KettleException e) {
            e.printStackTrace();
        }
        return null;
    }
}
