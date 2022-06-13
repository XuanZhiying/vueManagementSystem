package com.xuanzy.demo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuanzy.demo.entity.Menu;
import com.xuanzy.demo.entity.Music.Music;
import com.xuanzy.demo.mapper.FileMapper;
import com.xuanzy.demo.mapper.MenuMapper;
import com.xuanzy.demo.mapper.musicMapper.MusicMapper;
import com.xuanzy.demo.service.FileService;
import com.xuanzy.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Resource
    FileService fileService;
    @Resource
    FileMapper fileMapper;
    @Resource
    MenuMapper menuMapper;
    @Resource
    MusicMapper musicMapper;


    @Test
    void contextLoads() {
        String name = "";

        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(name)) {
            menuQueryWrapper.like("name", name);
            menuQueryWrapper.orderByDesc("id");
        }

        List<Menu> list = menuMapper.selectList(menuQueryWrapper);
        List<String> collect = list.stream().filter(menu -> menu.getPid() != null).map(menu -> {
            menu.setName("A_" + menu.getName());
            return menu.getName();
        })
                .collect(Collectors.toList());

        System.out.println(collect);
        //找出pid为null的一级菜单
        List<Menu> parentMenu = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());

        System.out.println(parentMenu);
        //找出一级菜单的子菜单
        for (Menu menu : parentMenu) {
            menu.setChildren(list.stream().filter(pMenu -> menu.getId().equals(pMenu.getPid())).collect(Collectors.toList()));
        }
        System.out.println(parentMenu);
    }

    @Test
    void HuTool() {
        Integer i = 5;
        String s = Convert.toStr(i);
        System.out.println(s);

       /* String a = "2017-05-06";
        Date value = Convert.toDate(a);
        System.out.println(value);
        System.out.println(DateUtil.date());
        System.out.println(DateUtil.now());
        System.out.println(DateUtil.today());
        String dateStr = "2017-03-01";
        Date date = DateUtil.parse(dateStr);
        System.out.println(date);
        System.out.println(DateUtil.year(date) + "------" + Convert.toInt(DateUtil.month(date)) + 1 + "-------" + DateUtil.monthEnum(date));
        System.out.println("一天的开始时间" + DateUtil.beginOfDay(DateUtil.date()) + "一天的结束时间" + DateUtil.endOfDay(DateUtil.date()));
        System.out.println(DateUtil.ageOfNow("1999-03-10"));
*/
        TimeInterval timer = DateUtil.timer();
        System.out.println(timer);

        // 分组1
        timer.start("1");
        ThreadUtil.sleep(500);

// 分组2
        timer.start("2");
        ThreadUtil.sleep(900);

        Console.log("Timer 1 took {} ms", timer.intervalMs("1"));
        Console.log("Timer 2 took {} ms", timer.intervalMs("2"));
    }
    @Test
    void musicData(){
        List<Music> music = musicMapper.selectAll();
        System.out.println(music);
    }
}
