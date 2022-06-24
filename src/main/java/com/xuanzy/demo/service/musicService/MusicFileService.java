package com.xuanzy.demo.service.musicService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.MusicFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 轩志颍
 * Create: 2022-06-06 17:56
 **/
public interface MusicFileService extends IService<MusicFile> {
    Result uploadSongFile(MultipartFile file) throws IOException;
    Result onlineOpenMusic(String uuid, HttpServletResponse httpServletResponse) throws IOException;
}
