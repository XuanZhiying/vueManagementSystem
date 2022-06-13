package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuanzy.demo.entity.RoleMenu;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-30 23:51
 **/
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Integer> selectByRoleId(Integer roleId);
}
