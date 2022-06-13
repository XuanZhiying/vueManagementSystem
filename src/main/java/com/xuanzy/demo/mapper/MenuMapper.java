package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 19:34
 **/
public interface MenuMapper extends BaseMapper<Menu> {
    IPage<Menu> selectMenuAndPage(Page<Menu> page,@Param("menu") Menu menu);
    List<Integer> selectFatherMenu();
}
