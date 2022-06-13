package com.xuanzy.demo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.service.UserService;

import java.util.Date;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-19 16:45
 **/
public class UserListener extends AnalysisEventListener<User> {

    private final UserService userService;

    public UserListener(UserService userService) { //有参构造函数，接收传递的service对象
        this.userService = userService;
    }

    /**
     * 单次缓存的数据量
     */
    public static final int BATCH_COUNT = 50;
    /**
     * 临时存储
     */
    private List<User> userList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);//临时存储

    @Override
    public void invoke(User data, AnalysisContext context) {
        data.setCreateTime(new Date());
        System.out.println("解析到一条数据：" + data);

        userList.add(data);
        if (userList.size() >= BATCH_COUNT) {//每50条存储一次
            userService.insertUserList(userList);

            userList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT); //清空存储后的list

        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        userService.insertUserList(userList);  //存储最后list中的数据
        System.out.println("数据全部解析完成");


    }
}
