package com.xuanzy.demo.service.musicService.musicServiceImpl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.Music;
import com.xuanzy.demo.entity.Music.MusicType;
import com.xuanzy.demo.mapper.musicMapper.MusicMapper;
import com.xuanzy.demo.mapper.musicMapper.MusicTypeMapper;
import com.xuanzy.demo.service.musicService.MusicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:52
 **/
@Service
@DS("mysqlmusicdata")
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

    @Resource
    MusicMapper musicMapper;
    @Resource
    MusicTypeMapper musicTypeMapper;

    @Value("${file.download.musicUrl}")
    private String musicOnlineOpenUrl;

    @Override
    public Result saveMusic(Music music) {
        if (music.getId() == null) {
            return Result.success(musicMapper.insert(music));
        } else {
            return Result.success(musicMapper.updateById(music));
        }
    }

    @Override
    public Result pageMusic(Integer current, Integer size, Music music) {
        Page<Music> musicPage = new Page<>(current, size);
        IPage<Music> musicIPage = musicMapper.selectMusicAndPage(musicPage, music);
        List<MusicType> musicTypes = musicTypeMapper.selectList(new QueryWrapper<>());

        for (int i=0;i<musicIPage.getRecords().size();i++){
            musicPage.getRecords().get(i).setMusicImageFileUrl(musicOnlineOpenUrl+musicIPage.getRecords().get(i).getMusicImageFileUuid());
            musicPage.getRecords().get(i).setMusicFileUrl(musicOnlineOpenUrl+musicIPage.getRecords().get(i).getMusicFileUuid());

            for (MusicType musicType : musicTypes) {
                if (musicPage.getRecords().get(i).getMusicType().equals(musicType.getId())) {
                    musicPage.getRecords().get(i).setMusicTypeName(musicType.getMusicType());
                }
            }
        }
        return Result.success(musicIPage);
    }

}
