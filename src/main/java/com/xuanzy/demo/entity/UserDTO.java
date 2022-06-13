package com.xuanzy.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-05-01 15:05
 **/
@Data
public class UserDTO {

    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menu;

}
