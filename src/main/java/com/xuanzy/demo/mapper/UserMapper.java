package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-18 08:22
 **/
public interface UserMapper extends BaseMapper<User> {
    List<User> selectAll();

    IPage<User> selectUserAndPage(Page<User> page, @Param("user") User user);

    Integer getCount();

    List<User> selectUserAndDownload(@Param("user")User user);

    boolean insertUserForeach(@Param("userlist")List<User> userList);

    User getUserByUserNameAndPassWord(@Param("user")User user);

    void dropTable();

    Integer countUser();
}
