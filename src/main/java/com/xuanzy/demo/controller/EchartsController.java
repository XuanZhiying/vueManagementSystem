package com.xuanzy.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.service.EChartService;
import com.xuanzy.demo.service.FileService;
import com.xuanzy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-28 12:44
 **/
@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Resource
    EChartService eChartService;


    @GetMapping("/members")
    public Result members(){


        return eChartService.getECharts();
    }
//    @GetMapping("/fileMembers")
//    public Result fileMembers(){
//        return eChartService.getEChartFiles();
//    }
}
