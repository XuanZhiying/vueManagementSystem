package com.xuanzy.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.interceptor.annotation.LoginToken;
import com.xuanzy.demo.interceptor.annotation.PassToken;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.service.FileService;
import com.xuanzy.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-18 09:21
 **/
@RestController
public class UserController {

    final
    UserService userService;
    final
    FileService fileService;

    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PassToken
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    @GetMapping("/getOneUser")
    public Result getOneUser(@RequestParam String id) {

        return new Result(Constants.CODE_200, "登录用户信息获取成功", userService.getById(id));
    }

    @PassToken
    @ResponseBody
    @GetMapping("/getAlluser")
    public Result getAllUser() {
        return Result.success(userService.findAll());
    }

    @PostMapping("/saveUser")
    public Integer saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @LoginToken
    @DeleteMapping("/deleteUser/{id}")
    public Integer deleteUser(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    /* @GetMapping("/userpage")
     public IPage<User> getPageUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "5") Integer pageSize,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam String email,
                                    @RequestParam String address) {
         User user = new User();
         user.setUsername(username)
         .setEmail(email)
         .setAddress(address);
         return userService.PageUser(pageNum, pageSize, user);

     }*/
    @LoginToken
    @GetMapping("/userPage")
    public IPage<User> getPageUser(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(defaultValue = "", value = "username") String username,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String address,
                                   @RequestParam(required = false) String createTime) throws ParseException {

        return userService.PageUser(pageNum, pageSize, setUsers(username, email, address, createTime));
    }


    @PostMapping("/deleteBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeByIds(ids);
    }

    @GetMapping("/userMessageDownload")
    public void userMessageDownload(HttpServletResponse response,
                                    @RequestParam(defaultValue = "") String username,
                                    @RequestParam String email,
                                    @RequestParam String address,
                                    @RequestParam String createTime) throws ParseException {
        userService.downloadUser(response, setUsers(username, email, address, createTime));

    }

    /**
     * 上传Excel文件读取，并写入数据库
     *
     * @param file
     * @return
     */
    @PostMapping("/import")
    public boolean importExcel(MultipartFile file) {
        return fileService.importExcelAndInsetUser(file);
    }


    private User setUsers(String username, String email, String address, String createTime) throws ParseException {
        User user = new User();
        if (createTime.equals("")) {
            user.setCreateTime(null);
        } else {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime);
            user.setCreateTime(date);
        }
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        return user;

    }
}
