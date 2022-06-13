package com.xuanzy.demo.service.serviceImpl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Menu;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.mapper.RoleMapper;
import com.xuanzy.demo.mapper.RoleMenuMapper;
import com.xuanzy.demo.mapper.UserMapper;
import com.xuanzy.demo.service.MenuService;
import com.xuanzy.demo.service.UserService;
import com.xuanzy.demo.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-18 08:35
 **/
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleMenuMapper roleMenuMapper;
    @Resource
    MenuService menuService;


    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public Integer saveUser(User user) {
        if (user.getId() == null) {
            user.setCreateTime(new Date());
            return userMapper.insert(user);//没有ID为新增
        } else {
            user.setUpdateTime(new Date());
            return userMapper.updateById(user);//有ID,通过ID进行更新
        }
    }

    @Override
    public IPage<User> PageUser(Integer current, Integer size, User user) {
        Page<User> userPage = new Page<>(current, size);

        return userMapper.selectUserAndPage(userPage, user);
    }


    @Override
    public void downloadUser(HttpServletResponse response, User user) {

        List<User> userList = userMapper.selectUserAndDownload(user);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // URLEncoder.encode可以防止中文乱码
        String fileName;
        fileName = URLEncoder.encode("用户信息表", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), User.class).sheet("Sheet1").doWrite(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertUserList(List<User> userList) {
        userMapper.insertUserForeach(userList);
    }

    @Override
    public Result login(User user) {
        if (user.getUsername().equals("") || user.getUsername() == null) {
            return new Result(Constants.CODE_401, "用户名为空！", null);
        }
        if (user.getPassword().equals("") || user.getPassword() == null) {
            return new Result(Constants.CODE_401, "密码为空！", null);
        }
        try {
            User userByUserNameAndPassWord = userMapper.getUserByUserNameAndPassWord(user);
            if (userByUserNameAndPassWord == null) {
                return new Result(Constants.CODE_300, "账户名或密码错误！", null);
            } else {
                String roleFlag = userByUserNameAndPassWord.getRole();

                List<Menu> roleMenu = getRoleMenu(roleFlag);

                userByUserNameAndPassWord.setMenu(roleMenu);

                String token = TokenUtils.getToken(userByUserNameAndPassWord);
                userByUserNameAndPassWord.setToken(token);
                return new Result(Constants.CODE_200, "", userByUserNameAndPassWord);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Constants.CODE_300, "SQL查询错误！", null);
        }

    }

    /**
     * 获取当前角色的菜单列表
     * @param roleFlag
     * @return
     */

    private List<Menu> getRoleMenu(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);

        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);


        //查找出所有菜单
        List<Menu> menus = menuService.findMenus("");
        //筛选之后的List
        ArrayList<Menu> roleMenus = new ArrayList<>();

        //筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            //移除children里面不在menuIds集合中的元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

    public Integer deleteUserById(Integer id) {
        return userMapper.deleteById(id);
    }

}
