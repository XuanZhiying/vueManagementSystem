package com.xuanzy.demo.service.serviceImpl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import com.xuanzy.demo.entity.User;
import com.xuanzy.demo.listener.UserListener;
import com.xuanzy.demo.mapper.FileMapper;
import com.xuanzy.demo.service.FileService;
import com.xuanzy.demo.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Author: 轩志颍
 * Create: 2022-04-20 11:02
 **/
@Service
@Transactional
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {

    @Resource
    FileMapper fileMapper;

    final UserService userService;

    public FileServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * 文件上传路径
     **/
    @Value("${file.upload.dir}")
    private String realPath;

    /**
     * 文件下载路径
     */
    @Value("${file.download.fileUrl}")
    private String fileDownloadUrl;

    /**
     * 图片在线打开路径
     */
    @Value("${file.download.pictureUrl}")
    private String pictureOnlineOpenUrl;

    @Override
    public boolean importExcelAndInsetUser(MultipartFile file) {
        String fileName = file.getOriginalFilename();  //获取文件名
        if (fileName == null) {
            return false;
        } else {
            String fileXlsx = fileName.substring(fileName.length() - 5);       //获取文件的后缀名为xlsx
            String fileXls = fileName.substring(fileName.length() - 4);
            if (!(fileXlsx.equals(".xlsx") || fileXls.equals(".xlsx"))) {
                return false;
            }
        }

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean a = false;
        try {
            EasyExcel.read(inputStream, User.class, new UserListener(userService)).sheet().doRead();
            a = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;

    }

    @Override
    public Result saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String newFileName = "";
        String type = "";
        UUID uuid = null;
        if (file.getOriginalFilename() != null) {
            type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            uuid = UUID.randomUUID();
            newFileName = uuid + "_" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + type;//修改新文件名为时间戳精准到毫秒，暂不设置随机数

        }
        long size = file.getSize();
        File uploadFile = new File(realPath + newFileName);

        String md5 = SecureUtil.md5(file.getInputStream());
        System.out.println(md5);
        Files files = new Files();

        files.setUuid(String.valueOf(uuid));
        Files filesByMD5 = fileMapper.getFilesByMD5(md5);
        if (filesByMD5 != null) {
            files.setUrl(filesByMD5.getUrl());
            files.setNewFileName(filesByMD5.getNewFileName());

        } else {
            files.setMd5(md5);
            file.transferTo(uploadFile);
            files.setUrl(String.valueOf(uploadFile));
            files.setNewFileName(newFileName);

        }

        files.setUploadTime(new Date());
        files.setOldFileName(originalFilename);
        files.setSize(Math.toIntExact(size / 1024));
        files.setType(type);

        String downloadUrl = "http://localhost:8085/file/";


        saveFileMessage(files);
        return new Result(Constants.CODE_200, "文件信息保存成功", downloadUrl + uuid);

    }

    @Override
    public void saveFileMessage(Files files) {
        fileMapper.insert(files);
    }

    @Override
    public Result downloadFile(String uuid, HttpServletResponse httpServletResponse) throws IOException {
        HashMap<String, Object> objectHashMap = getFile(uuid);
        Files files = (Files) objectHashMap.get("files");
        FileInputStream fileInputStream = (FileInputStream) objectHashMap.get("fileInputStream");

        // 获取响应前。设置以附件形式下载 attachment:附件
        httpServletResponse.setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode(files.getOldFileName(), StandardCharsets.UTF_8));
        //在线打开图片
      /*  httpServletResponse.setHeader("content-disposition", "inline;filename="
                + URLEncoder.encode(files.getOldFileName(), StandardCharsets.UTF_8));*/
        //获取响应输出流
        ServletOutputStream os = httpServletResponse.getOutputStream();
        //输入流复制给输出流
        if (fileInputStream != null) {
            FileCopyUtils.copy(fileInputStream, os);
        }
        return new Result(Constants.CODE_200, "文件下载成功", files.getOldFileName());
    }

    private HashMap<String, Object> getFile(String uuid) {
        HashMap<String, Object> objectHashMap = new HashMap<>();

        Files files = new Files();
        try {
            files = fileMapper.getFilesByUuid(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = null;
        try {
            //去指定的目录中读取文件
            file = new File(realPath, files.getNewFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //首先判断读取文件是否为空，为空，删除记录
        try {
            if (file != null) {
                FileInputStream is = new FileInputStream(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
            fileMapper.deleteById(files.getId());
        }
        //将文件读取为文件输入流
        FileInputStream fileInputStream = null;
        if (file != null) {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        objectHashMap.put("files", files);
        objectHashMap.put("fileInputStream", fileInputStream);
        return objectHashMap;
    }

    @Override
    public Result pageFiles(Integer current, Integer size, Files files) {

        Page<Files> filesPage = new Page<>(current, size);
        IPage<Files> filesIPage = fileMapper.selectFilesAndPage(filesPage, files);
        for (int i = 0; i < filesIPage.getRecords().size(); i++) {
            String fileType = filesIPage.getRecords().get(i).getType();
            if (fileType.equals(".jpg") || fileType.equals(".png") || fileType.equals(".jpeg")) {
                filesIPage.getRecords().get(i).setUrl(pictureOnlineOpenUrl + filesIPage.getRecords().get(i).getUuid());
            } else {
                filesIPage.getRecords().get(i).setUrl(fileDownloadUrl + filesIPage.getRecords().get(i).getUuid());
            }
        }
        return new Result(Constants.CODE_200, "分页信息查询成功", filesIPage);
    }

    @Override
    public Result deleteFiles(Integer id) {
        return new Result(Constants.CODE_200, "删除成功", fileMapper.deleteById(id));
    }

    @Override
    public int updateFiles(Files files) {
        Files files1 = new Files();
        files1.setId(files.getId());
        files1.setIsDelete(files.getIsDelete());
        return fileMapper.updateById(files1);
    }

    /**
     * 在线打开图片
     *
     * @param uuid
     * @param httpServletResponse
     * @return
     * @throws IOException
     */

    @Override
    public Result onlineOpenPicture(String uuid, HttpServletResponse httpServletResponse) throws IOException {
        HashMap<String, Object> objectHashMap = getFile(uuid);
        Files files = (Files) objectHashMap.get("files");
        FileInputStream fileInputStream = (FileInputStream) objectHashMap.get("fileInputStream");

        //在线打开图片
        httpServletResponse.setHeader("content-disposition", "inline;filename="
                + URLEncoder.encode(files.getOldFileName(), StandardCharsets.UTF_8));
        //获取响应输出流
        ServletOutputStream os = httpServletResponse.getOutputStream();
        //输入流复制给输出流
        if (fileInputStream != null) {
            FileCopyUtils.copy(fileInputStream, os);
        }
        return new Result(Constants.CODE_200, "文件在线打开成功", files.getOldFileName());
    }
}
