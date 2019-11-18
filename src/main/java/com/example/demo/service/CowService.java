package com.example.demo.service;

import com.example.demo.entity.CowInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author YM
 * @date 2019/10/29 17:34
 */
public interface CowService {

    int add(CowInfo cow);

    void deleteByKey(int id);

    void update(CowInfo cowInfo);

    PageInfo getByPage(int page, int pageSize);
}
