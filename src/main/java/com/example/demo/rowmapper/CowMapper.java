package com.example.demo.rowmapper;

import com.example.demo.entity.CowInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author YM
 * @date 2019/11/15 15:03
 */
public class CowMapper implements RowMapper<CowInfo> {
    @Override
    public CowInfo mapRow(ResultSet resultSet, int i) throws SQLException {
        CowInfo cowInfo = new CowInfo();
        cowInfo.setId(resultSet.getLong("id"));
        cowInfo.setName(resultSet.getString("name"));
        cowInfo.setAddress(resultSet.getString("address"));
        cowInfo.setPhone(resultSet.getString("phone"));
        cowInfo.setAmount(resultSet.getInt("amount"));
        cowInfo.setCreateDate(resultSet.getDate("create_date"));
        return cowInfo;
    }
}
