package com.xuanzy.demo.controller;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.Music;
import com.xuanzy.demo.service.musicService.MusicService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:58
 **/
@RestController
@RequestMapping("/music")
public class MusicController {
    @Resource
    MusicService musicService;


    @GetMapping("/pageMusic")
    public Result pageMusic(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "5") Integer pageSize,
                            @RequestParam String musicType) {
        Music music = new Music();

        if (!musicType .equals("")) {
            music.setMusicType(Integer.valueOf(musicType));
        }

        return musicService.pageMusic(pageNum, pageSize, music);
    }

    @PostMapping("/saveMusic")
    public Result savaMusic(MultipartFile file, @RequestBody Music music) {
        return musicService.saveMusic(file, music);
    }
    @DeleteMapping("/deleteMusic/{id}")
    public Result deleteMusicById(@PathVariable Integer id){
        return Result.success(musicService.removeById(id));
    }
    @PostMapping("/deleteMusicBatch")
    public Result deleteMusicByBatch(@RequestBody List<Integer> ids){
        return Result.success(musicService.removeByIds(ids));
    }

}
