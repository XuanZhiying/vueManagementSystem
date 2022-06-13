package com.xuanzy.demo.service.serviceImpl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Menu;
import com.xuanzy.demo.entity.Role;
import com.xuanzy.demo.entity.RoleMenu;
import com.xuanzy.demo.mapper.MenuMapper;
import com.xuanzy.demo.mapper.RoleMapper;
import com.xuanzy.demo.mapper.RoleMenuMapper;
import com.xuanzy.demo.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 11:19
 **/
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    RoleMapper roleMapper;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    MenuMapper menuMapper;

    @Override
    public Result pageRole(Integer pageNum, Integer pageSize, Role role) {
        return Result.success(roleMapper.selectRoleAndPage(new Page<>(pageNum,pageSize),role));
    }

    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",roleId);
        //先删除当前角色id所有绑定关系
        roleMenuMapper.delete(roleMenuQueryWrapper);

        ArrayList<Integer> menuIdsCopy = CollUtil.newArrayList(menuIds);

        //再绑定前端传输的菜单id到角色id上去
        for (Integer menuId : menuIds) {
            Menu menu = menuMapper.selectById(menuId);
            if (menu.getPid()!=null && !menuIdsCopy.contains(menu.getPid())){ //二级菜单，且传过来的menuIds中没有父级id
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public Result saveRole(Role role) {
        if (role.getId()==null){
            roleMapper.insert(role);
        }else {
            roleMapper.updateById(role);
        }
        return Result.success();
    }
}
