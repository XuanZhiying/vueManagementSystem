package com.xuanzy.demo.controller;

import com.xuanzy.demo.common.Constants;
import com.xuanzy.demo.common.Result;
import com.xuanzy.demo.entity.Files;
import com.xuanzy.demo.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-21 20:36
 **/

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    FileService fileService;


    @PostMapping("/uploadFile")
    public Result fileUpload(@RequestParam MultipartFile file) throws IOException {

        return fileService.saveFile(file);
    }

    @GetMapping("/{uuid}")
    public Result getFile(@PathVariable String uuid, HttpServletResponse httpServletResponse) throws IOException {
        return fileService.downloadFile(uuid, httpServletResponse);
    }

    @GetMapping("/getFilesAndPage")
    public Result getFilesAndPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "8") Integer pageSize,
                                  @RequestParam String fileName,
                                  @RequestParam String size,
                                  @RequestParam String uploadTime
    ) throws ParseException {
        Files files = new Files();
        if (uploadTime.equals("")) {
            files.setUploadTime(null);
        } else {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(uploadTime);
            files.setUploadTime(date);
        }
        if (size.equals("")) {
            files.setSize(null);
        } else {
            files.setSize(Integer.valueOf(size));
        }
        files.setOldFileName(fileName);

        return fileService.pageFiles(pageNum, pageSize, files);

    }

    @DeleteMapping("/deleteFile/{id}")
    public Result deleteFile(@PathVariable Integer id){
        return new Result(Constants.CODE_200,"文件删除成功",fileService.removeById(id));
    }
    @PostMapping("/deleteFilesBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return fileService.removeByIds(ids);
    }

    @PostMapping("/updateFile")
    public Result updateFile(@RequestBody Files files){
        return Result.success(fileService.updateFiles(files));
    }
}
