package com.xuanzy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 轩志颍
 * Create: 2022-04-20 11:01
 **/
public interface FileService extends IService<Files> {
    boolean importExcelAndInsetUser(MultipartFile file);
    Result saveFile(MultipartFile file) throws IOException;

    void saveFileMessage(Files files);

    Result downloadFile(String uuid, HttpServletResponse httpServletResponse) throws IOException;

    Result pageFiles(Integer current, Integer size, Files files);

    Result deleteFiles(Integer id);

    int updateFiles(Files files);

    Result onlineOpenPicture(String uuid, HttpServletResponse httpServletResponse) throws IOException;
}
