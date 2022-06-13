package com.xuanzy.demo.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Dict;
import com.xuanzy.demo.entity.Menu;
import com.xuanzy.demo.mapper.DictMapper;
import com.xuanzy.demo.mapper.MenuMapper;
import com.xuanzy.demo.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 19:57
 **/
@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    MenuMapper menuMapper;
    @Resource
    DictMapper dictMapper;

    @Override
    public Result pageMenu(Integer pageNum, Integer pageSize, Menu menu) {
        return Result.success(menuMapper.selectMenuAndPage(new Page<>(pageNum, pageSize), menu));
    }

    @Override
    public Result saveMenu(Menu menu) {
        if (menu.getId() == null) {
            return Result.success(menuMapper.insert(menu));
        } else {
            return Result.success(menuMapper.updateById(menu));
        }
    }

    @Override
    public Result findAllMenu(String name) {
        return Result.success(findMenus(name));
    }


    @Override
    public Result getAllIcons() {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictMapper.selectList(dictQueryWrapper));
    }

    @Override
    public List<Menu> findMenus(String name) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(name)){
            menuQueryWrapper.like("name", name);
            menuQueryWrapper.orderByDesc("id");
        }

        List<Menu> list = menuMapper.selectList(menuQueryWrapper);
        //找出pid为null的一级菜单
        List<Menu> parentMenu = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        //找出一级菜单的子菜单
        for (Menu menu : parentMenu) {
            menu.setChildren(list.stream().filter(pMenu -> menu.getId().equals(pMenu.getPid())).collect(Collectors.toList()));
        }
        return parentMenu;
    }

    @Override
    public List<Integer> getFatherMenu() {
        return menuMapper.selectFatherMenu();
    }
}
