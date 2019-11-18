package com.example.demo.utils;

import com.example.demo.entity.CowInfo;
import com.example.demo.rowmapper.CowMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author YM
 * @date 2019/11/18 15:38
 */
public class DrillUtil {
    public void testDrill(){
        try {
            Class.forName("org.apache.drill.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:drill:drillbit=127.0.0.1");
            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from test.cow.cow_info");
//            if(resultSet.next()){
//                System.out.println(resultSet.getString("name"));
//            }

            ResultSet resultSet = statement.executeQuery("select * from oracle_test.COMM.USERS limit 10 offset 1");
            if(resultSet.next()){
                System.out.println(resultSet.getString("DB_USER"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void jdbcTemplateDrill(){

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.apache.drill.jdbc.Driver");
        dataSourceBuilder.url("jdbc:drill:drillbit=127.0.0.1");
        DataSource dataSource = dataSourceBuilder.build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<CowInfo> cowInfoList = jdbcTemplate.query("select * from test.cow.cow_info", new CowMapper());
        System.out.println(cowInfoList);
    }

    public static void main(String[] args) {
        jdbcTemplateDrill();
    }
}
