package com.xuanzy.demo.service.musicService.musicServiceImpl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Music.MusicFile;
import com.xuanzy.demo.mapper.musicMapper.MusicFileMapper;
import com.xuanzy.demo.service.musicService.MusicFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Author: 轩志颍
 * Create: 2022-06-07 18:58
 **/
@Service
@DS("mysqlmusicdata")
public class MusicFileServiceImpl extends ServiceImpl<MusicFileMapper, MusicFile> implements MusicFileService {
    @Resource
    MusicFileMapper musicFileMapper;

    /**
     * 文件上传路径
     **/
    @Value("${file.upload.dir}")
    private String realPath;

    @Override
    public Result uploadSongFile(MultipartFile file) throws IOException {
        MusicFile musicFile = new MusicFile();
        UUID uuid = UUID.randomUUID();
        musicFile.setSongUuid(String.valueOf(uuid));

        String type;
        String newFileName = "";
        if (file.getOriginalFilename() != null) {
            musicFile.setSongName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
            type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + type;//修改新文件名为时间戳精准到毫秒，暂不设置随机数

            musicFile.setFileType(type);
        }
        File uploadFile = new File(realPath + newFileName);

        String md5 = SecureUtil.md5(file.getInputStream());
        System.out.println("md5--------" + md5);
        MusicFile musicFileMd5 = musicFileMapper.selectOneByMd5(md5);

        if (musicFileMd5 == null) {
            file.transferTo(uploadFile);
            musicFile.setSongMd5(md5);
            musicFile.setUploadName(newFileName);
        } else {
            musicFile.setUploadName(musicFileMd5.getUploadName());
        }
        musicFile.setUploadTime(new Date());
        musicFileMapper.insert(musicFile);

        return new Result(Constants.CODE_200, "歌曲文件保存成功", String.valueOf(uuid));
    }
}
