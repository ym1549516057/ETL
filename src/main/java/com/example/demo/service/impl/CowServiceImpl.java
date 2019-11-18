package com.example.demo.service.impl;

import com.example.demo.dao.CowMapper;
import com.example.demo.entity.CowInfo;
import com.example.demo.service.CowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YM
 * @date 2019/10/29 17:34
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CowServiceImpl implements CowService {
    @Resource
    private CowMapper cowMapper;

    @Override
    public int add(CowInfo cow) {
        return cowMapper.insertUseGeneratedKeys(cow);
    }

    @Override
    public void deleteByKey(int id) {
        cowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CowInfo cowInfo) {
        cowMapper.updateByPrimaryKey(cowInfo);
    }

    @Override
    public PageInfo<CowInfo> getByPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<CowInfo> cowInfoList = cowMapper.selectAll();
        PageInfo<CowInfo> pageInfo = new PageInfo<>(cowInfoList);
        return pageInfo;
    }
}
