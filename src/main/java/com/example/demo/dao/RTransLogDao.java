package com.example.demo.dao;

import com.example.demo.entity.RTransLog;
import com.example.demo.mymapper.MyMapper;

import java.util.List;

/**
 * @author YM
 * @date 2019/10/29 16:33
 */
public interface RTransLogDao extends MyMapper<RTransLog> {

    List<RTransLog> getAll();
}
