package com.xuanzy.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Role;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 11:18
 **/
public interface RoleService extends IService<Role> {
    Result pageRole(Integer pageNum, Integer pageSize, Role role);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);

    Result saveRole(Role role);
}
