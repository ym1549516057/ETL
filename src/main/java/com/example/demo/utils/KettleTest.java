package com.example.demo.utils;

import com.example.demo.properties.Kettle;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.ProgressNullMonitorListener;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.core.logging.LogChannelFactory;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingHierarchy;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import java.util.List;

/**
 * @author YM
 * @date 2019/11/14 15:59
 */
public class KettleTest {


    public static String runResource1(String transName, Kettle kettle) {
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = createOne(repository, kettle);
            ProgressMonitorListener monitorListener = new ProgressNullMonitorListener();

            //选择转换
            TransMeta transMeta = repository.loadTransformation(transName, repositoryDirectoryInterface, monitorListener, true, null);
            transMeta.setLogLevel(LogLevel.ROWLEVEL);

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
//            trans.setVariable("value","'XUKP'");

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

    public static String runResource2(String transName) {
        try {
            KettleEnvironment.init();
            //创建db资源库
            KettleDatabaseRepository repository = new KettleDatabaseRepository();
            RepositoryDirectoryInterface repositoryDirectoryInterface = createTwo(repository);
            ProgressMonitorListener monitorListener = new ProgressNullMonitorListener();

            //选择转换
            TransMeta transMeta = repository.loadTransformation(transName, repositoryDirectoryInterface, monitorListener, true, null);
            transMeta.setLogLevel(LogLevel.ROWLEVEL);

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
//            trans.setVariable("value","'XUKP'");

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
     * 连接到资源库
     *
     * @param
     * @return
     */
    private static RepositoryDirectoryInterface createOne( KettleDatabaseRepository repository,Kettle kettle) {
        try {
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

    /**
     * 连接到资源库
     *
     * @param
     * @return
     */
    private static RepositoryDirectoryInterface createTwo(KettleDatabaseRepository repository) {
        try {
            DatabaseMeta databaseMeta = new DatabaseMeta("test-kettle", "mysql", "jdbc", "129.226.133.70", "test-resource", "3306", "root", "123456");
            //选择资源库
            KettleDatabaseRepositoryMeta kettleDatabaseRepositoryMeta = new KettleDatabaseRepositoryMeta("kettle", "resource", "描述", databaseMeta);
            repository.init(kettleDatabaseRepositoryMeta);
            //连接资源库
            repository.connect("admin", "admin");
            return repository.loadRepositoryDirectoryTree();
        } catch (KettleException e) {
            e.printStackTrace();
        }
        return null;
    }
}
