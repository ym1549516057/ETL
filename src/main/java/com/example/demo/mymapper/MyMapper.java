package com.example.demo.mymapper;


import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author YM
 * @date 2019/10/29 17:23
 * 不能放在dao层下
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
