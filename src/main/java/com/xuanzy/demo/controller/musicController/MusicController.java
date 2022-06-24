package com.xuanzy.demo.controller.musicController;

import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.Music;
import com.xuanzy.demo.service.musicService.MusicService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:58
 **/
@RestController
@RequestMapping("/music")
@Api(value = "音乐信息新增修改管理",tags = "音乐管理")
public class MusicController {
    @Resource
    MusicService musicService;


    @ApiOperation(value = "分页信息查询")
    @GetMapping("/pageMusic")
    public Result pageMusic(@ApiParam(value = "当前页码",example = "1") @RequestParam(defaultValue = "1") Integer pageNum,
                            @ApiParam(value = "每页总数",example = "5")@RequestParam(defaultValue = "5") Integer pageSize,
                            @ApiParam(value = "音乐类型搜索，Integer类型",example = "1")@RequestParam(required = false) Integer musicType) {
        Music music = new Music();

        if (musicType!=null) {
            music.setMusicType(musicType);
        }

        return musicService.pageMusic(pageNum, pageSize, music);
    }

    @ApiOperation(value = "音乐信息保存")
    @PostMapping("/saveMusic")
    public Result savaMusic(@ApiParam(value = "音乐实体类")@RequestBody Music music) {
        return musicService.saveMusic(music);
    }
    @ApiOperation(value = "音乐信息删除")
    @DeleteMapping("/deleteMusic/{id}")
    public Result deleteMusicById(@ApiParam(value = "主键ID",example = "1")@PathVariable Integer id){
        return Result.success(musicService.removeById(id));
    }

    @ApiOperation(value = "音乐信息批量删除")
    @PostMapping("/deleteMusicBatch")
    public Result deleteMusicByBatch(@ApiParam(value = "ID数组",example = "[1,5,6]")@RequestBody List<Integer> ids){
        return Result.success(musicService.removeByIds(ids));
    }

}
