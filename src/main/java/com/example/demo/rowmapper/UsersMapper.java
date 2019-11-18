package com.example.demo.rowmapper;

import com.example.demo.entity.Users;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author YM
 * @date 2019/11/15 15:35
 */
public class UsersMapper implements RowMapper<Users> {
    @Override
    public Users mapRow(ResultSet resultSet, int i) throws SQLException {
        Users users = new Users();
        users.setDbUser(resultSet.getString("DB_USER"));
        users.setUserDept(resultSet.getString("USER_DEPT"));
        return users;
    }
}
