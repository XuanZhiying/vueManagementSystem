package com.xuanzy.demo.controller;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Menu;
import com.xuanzy.demo.mapper.DictMapper;
import com.xuanzy.demo.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author: 轩志颍
 * Create: 2022-04-29 19:37
 **/
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    MenuService menuService;

    @GetMapping("/findAll")
    public Result findAll(@RequestParam(defaultValue = "") String name) {
        return menuService.findAllMenu(name);
    }

    @GetMapping("/pageMenu")
    public Result pageMenu(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {
        Menu menu = new Menu();
        menu.setName(name);
        return menuService.pageMenu(pageNum, pageSize, menu);
    }
    @GetMapping("/ids")
    public Result ids(){
        return Result.success(menuService.list().stream().map(Menu::getId));
    }

    @PostMapping("/saveMenu")
    public Result saveMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @DeleteMapping("/deleteMenu/{id}")
    public Result deleteMenu(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    @GetMapping("/icons")
    public Result getIcons() {
        return menuService.getAllIcons();
    }
}
