package com.xuanzy.demo.service.musicService.musicServiceImpl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import com.xuanzy.demo.entity.Music.MusicFile;
import com.xuanzy.demo.exception.ServiceException;
import com.xuanzy.demo.mapper.musicMapper.MusicFileMapper;
import com.xuanzy.demo.service.musicService.MusicFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

    @Value("${file.download.musicUrl}")
    private String musicOnlineOpenUrl;

    @Override
    public Result uploadSongFile(MultipartFile file) throws IOException {
        MusicFile musicFile = new MusicFile();
        UUID uuid = UUID.randomUUID();
        musicFile.setFileUuid(String.valueOf(uuid));

        String type;
        String newFileName = "";
        if (file.getOriginalFilename() != null) {
            musicFile.setFileName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")));
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
            musicFile.setFileMd5(md5);
            musicFile.setUploadName(newFileName);
        } else {
            musicFile.setUploadName(musicFileMd5.getUploadName());
        }
        musicFile.setUploadTime(new Date());
        musicFileMapper.insert(musicFile);
        musicFile.setUrl(musicOnlineOpenUrl+musicFile.getFileUuid());

        return new Result(Constants.CODE_200, "歌曲文件保存成功", musicFile);
    }

    private HashMap<String, Object> getFile(String uuid) {
        HashMap<String, Object> objectHashMap = new HashMap<>();

        MusicFile musicFile = new MusicFile();
        try {
            musicFile = musicFileMapper.selectFileByUuid(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = null;
        try {
            //去指定的目录中读取文件
            file = new File(realPath, musicFile.getUploadName());
        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_400,"文件读取失败，请再次尝试");

        }

        //首先判断读取文件是否为空，为空，删除记录
        try {
            FileInputStream is = new FileInputStream(file);

        } catch (IOException e) {
            e.printStackTrace();
            musicFileMapper.deleteById(musicFile.getId());
            throw new ServiceException(Constants.CODE_400,"文件为空，已删除记录");
        }
        //将文件读取为文件输入流
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        objectHashMap.put("files", musicFile);
        objectHashMap.put("fileInputStream", fileInputStream);
        return objectHashMap;
    }
    @Override
    public Result onlineOpenMusic(String uuid, HttpServletResponse httpServletResponse) throws IOException {
        HashMap<String, Object> objectHashMap = getFile(uuid);
        MusicFile files = (MusicFile) objectHashMap.get("files");
        FileInputStream fileInputStream = (FileInputStream) objectHashMap.get("fileInputStream");

        //在线打开图片
        httpServletResponse.setHeader("content-disposition", "inline;filename="
                + URLEncoder.encode(files.getFileName(), StandardCharsets.UTF_8));
        //获取响应输出流
        ServletOutputStream os = httpServletResponse.getOutputStream();
        //输入流复制给输出流
        if (fileInputStream != null) {
            FileCopyUtils.copy(fileInputStream, os);
        }
        return new Result(Constants.CODE_200, "歌曲文件在线打开成功", files.getFileName());
    }
}
