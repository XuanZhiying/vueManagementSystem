package com.xuanzy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Menu;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 19:55
 **/
public interface MenuService extends IService<Menu> {
    Result pageMenu(Integer pageNum, Integer pageSize, Menu menu);

    Result saveMenu(Menu menu);

    Result findAllMenu(String name);

    Result getAllIcons();

    List<Menu> findMenus(String name);

    List<Integer> getFatherMenu();
}
