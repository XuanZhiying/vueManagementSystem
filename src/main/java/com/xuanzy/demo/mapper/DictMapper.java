package com.xuanzy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuanzy.demo.entity.Dict;

import java.util.List;

/**
 * Author: 轩志颍
 * Create: 2022-04-30 22:19
 **/
public interface DictMapper extends BaseMapper<Dict> {
    List<Dict> selectAllDict();

}
