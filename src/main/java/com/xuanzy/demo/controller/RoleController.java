package com.xuanzy.demo.controller;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Role;
import com.xuanzy.demo.mapper.RoleMapper;
import com.xuanzy.demo.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 11:20
 **/
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    RoleService roleService;

    @PostMapping("/saveRole")
    public Result saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/pageRole")
    public Result pageRole(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        Role role = new Role();
        role.setName(name);
        return roleService.pageRole(pageNum, pageSize, role);
    }
    @DeleteMapping("/deleteRole/{id}")
    public Result deleteRole(@PathVariable Integer id){
        return Result.success(roleService.removeById(id));
    }

    /**
     * 绑定角色与菜单的关系
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds){
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }
    @GetMapping("/getRoleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId){
        return Result.success(roleService.getRoleMenu(roleId));
    }
    @GetMapping("/role")
    public Result role(){
        return Result.success(roleService.list());
    }
}
