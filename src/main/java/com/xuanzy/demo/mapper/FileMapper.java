package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuanzy.demo.entity.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-22 15:46
 **/
public interface FileMapper extends BaseMapper<Files> {
    Files getFilesByUuid(String uuid);

    Files getFilesByMD5(String md5);

    IPage<Files> selectFilesAndPage(Page<Files> filesPage,@Param("files") Files files);

    List<Files> selectAllFiles();

    Integer countFiles();
}
