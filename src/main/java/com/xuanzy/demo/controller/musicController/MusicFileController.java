package com.xuanzy.demo.controller.musicController;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.service.musicService.MusicFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 17:54
 **/
@RestController
@RequestMapping("/musicFile")
public class MusicFileController {

    @Resource
    MusicFileService musicFileService;

    @PostMapping("/uploadSongFile")
    public Result uploadSongFile(MultipartFile file) throws IOException {
        return musicFileService.uploadSongFile(file);
    }
    @GetMapping("/{uuid}")
    public Result onlineOpenFile(@PathVariable String uuid,HttpServletResponse httpServletResponse) throws IOException {
        return musicFileService.onlineOpenMusic(uuid,httpServletResponse);
    }
}
