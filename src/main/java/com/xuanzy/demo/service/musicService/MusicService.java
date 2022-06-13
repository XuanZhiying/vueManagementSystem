package com.xuanzy.demo.service.musicService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.Music;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 14:52
 **/
public interface MusicService extends IService<Music> {
    Result saveMusic(MultipartFile file,Music music);

    Result pageMusic(Integer current,Integer size,Music music);
}
