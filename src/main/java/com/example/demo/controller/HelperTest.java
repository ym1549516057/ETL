package com.example.demo.controller;

import com.example.demo.entity.CowInfo;
import com.example.demo.service.CowService;
import com.example.demo.service.RtransService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YM
 * @date 2019/10/29 17:29
 */
@RestController
@RequestMapping("helper")
public class HelperTest {


    @Resource
    private CowService cowService;

    @PostMapping("addCow")
    public String addCow(CowInfo cowInfo) {
        cowService.add(cowInfo);
        return "添加成功!";
    }

    @GetMapping("delete")
    public void delete(int id) {
        cowService.deleteByKey(id);
    }

    @GetMapping("getByPage")
    public PageInfo getByList(int page, int rows) {
        return cowService.getByPage(page, rows);
    }
}
