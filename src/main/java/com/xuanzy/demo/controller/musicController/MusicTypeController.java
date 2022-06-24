package com.xuanzy.demo.controller.musicController;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.MusicType;
import com.xuanzy.demo.service.musicService.MusicTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-06-22 19:15
 **/
@RestController
@RequestMapping("/musicType")
public class MusicTypeController {
    @Resource
    MusicTypeService musicTypeService;

    @GetMapping("/pageMusicType")
    public Result pageMusicType(@RequestParam(defaultValue = "1", value = "num") Integer pageNum,
                                @RequestParam(defaultValue = "5", value = "size") Integer pageSize,
                                @RequestParam(required = false) String musicType) {
        MusicType type = new MusicType();
        if (musicType!=null&&!musicType.equals("")) {
            type.setMusicType(musicType);
        }
        return musicTypeService.pageMusicType(pageNum, pageSize, type);
    }

    @PostMapping("/saveMusicType")
    public Result saveMusicType(@RequestBody MusicType musicType) {
        return musicTypeService.saveMusicType(musicType);
    }

    @DeleteMapping("/deleteMusicType/{id}")
    public Result deleteMusicById(@PathVariable Integer id) {
        return Result.success(musicTypeService.removeById(id));
    }
    @PostMapping("/deleteMusicTypeBatch")
    public Result deleteMusicByBatch(@RequestBody List<Integer> ids){
        return Result.success(musicTypeService.removeByIds(ids));
    }
    @GetMapping("/getAllMusicType")
    public Result getAllMusicType(){
        return Result.success(musicTypeService.list());
    }
}
