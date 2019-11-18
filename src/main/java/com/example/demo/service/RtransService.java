package com.example.demo.service;

import com.example.demo.dao.RTransLogDao;
import com.example.demo.entity.RTransLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YM
 * @date 2019/10/29 15:14
 */
@Service
public class RtransService {

    @Resource
    private RTransLogDao rTransLogDao;

    public List<RTransLog> getAll(){
        return rTransLogDao.getAll();
    }
}
