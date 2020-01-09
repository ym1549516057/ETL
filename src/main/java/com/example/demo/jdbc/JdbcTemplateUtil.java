package com.example.demo.jdbc;

import com.example.demo.entity.CowInfo;
import com.example.demo.entity.Users;
import com.example.demo.rowmapper.CowMapper;
import com.example.demo.rowmapper.UsersMapper;
import org.hibernate.sql.Select;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author YM
 * @date 2019/11/15 14:42
 */
public class JdbcTemplateUtil {

    @Cacheable(value = "datasource",key = "args")
    public DataSource creatMysqlDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://129.226.133.70:3306/cow?createDatabaseIfNotExist=true");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("123456");
        DataSource dataSource = dataSourceBuilder.build();
        return dataSource;

    }

    public static void add(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<CowInfo> cowInfoList = jdbcTemplate.query("select * from cow_info", new CowMapper());
        System.out.println(cowInfoList);
    }

    public static void creatOracle() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:oracle:thin:@192.168.1.17:1521:orcl");
        dataSourceBuilder.username("system");
        dataSourceBuilder.password("china2012");
        dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
        DataSource dataSource = dataSourceBuilder.build();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "select * from users where db_user like '%X'";
        jdbcTemplate.execute(sql);
//        List<Users> usersList = jdbcTemplate.query(sql, new UsersMapper());
//        System.out.println(usersList);
    }


    public static void main(String[] args) {
//        JdbcTemplateUtil jdbcTemplateUtil = new JdbcTemplateUtil();
//        DataSource dataSource = jdbcTemplateUtil.creatMysqlDataSource();
//        add(dataSource);

        creatOracle();
    }
}
