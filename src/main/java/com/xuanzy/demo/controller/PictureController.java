package com.xuanzy.demo.controller;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 轩志颍
 * Create: 2022-05-22 11:12
 **/
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    FileService fileService;
    @GetMapping("/{uuid}")
    public Result getFile(@PathVariable String uuid, HttpServletResponse httpServletResponse) throws IOException {
        return fileService.onlineOpenPicture(uuid, httpServletResponse);
    }
}
