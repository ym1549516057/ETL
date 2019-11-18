package com.example.demo.jdbc;

import java.sql.*;

/**
 * @author YM
 * @date 2019/11/14 18:06
 */
public class JDBCUtil {

    public static void creat() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            String url = "jdbc:mysql://129.226.133.70:3306/cow?createDatabaseIfNotExist=true";
            String username = "root";
            String passwod = "123456";
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, passwod);
            String sql = "select * from cow_info where name like ?";
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(sql);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%测试%");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                System.out.println(name);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String[] args) {
        creat();
    }

}
