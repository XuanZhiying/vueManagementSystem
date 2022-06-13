package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 11:18
 **/
public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> selectRoleAndPage(Page<Role> rolePage,@Param("role") Role role);

    Integer selectByFlag(@Param("flag") String flag);
}
