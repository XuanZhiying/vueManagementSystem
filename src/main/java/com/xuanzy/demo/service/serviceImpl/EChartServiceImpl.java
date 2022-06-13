package com.xuanzy.demo.service.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.mapper.FileMapper;
import com.xuanzy.demo.mapper.UserMapper;
import com.xuanzy.demo.service.EChartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-28 19:11
 **/
@Service
@Transactional
public class EChartServiceImpl implements EChartService {
    @Resource
    UserMapper userMapper;
    @Resource
    FileMapper fileMapper;

    @Override
    public Result getEChartUser() {
        List<User> userList = userMapper.selectAll();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : userList) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @Override
    public Result getEChartFiles() {
        List<Files> list = fileMapper.selectAllFiles();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (Files files : list) {
            Date createTime = files.getUploadTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));

    }

    @Override
    public Result getECharts() {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("user",getEChartUser());
        objectObjectHashMap.put("file",getEChartFiles());

        objectObjectHashMap.put("userCount",userMapper.countUser());
        objectObjectHashMap.put("fileCount",fileMapper.countFiles());

        return Result.success(objectObjectHashMap);
    }
}
