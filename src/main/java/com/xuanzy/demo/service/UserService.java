package com.xuanzy.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-18 08:33
 **/
public interface UserService extends IService<User> {
    List<User> findAll();

    Integer deleteUserById(Integer id);

    Integer saveUser(User user);

    IPage<User> PageUser(Integer current, Integer size, User user);

    void downloadUser(HttpServletResponse response, User user);

    void insertUserList(List<User> userList);

    Result login(User user);






}
