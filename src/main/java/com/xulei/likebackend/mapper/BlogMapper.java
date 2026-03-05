package com.xulei.likebackend.mapper;

import com.xulei.likebackend.model.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xl
* @description 针对表【blog】的数据库操作Mapper
* @createDate 2026-03-05 22:32:51
* @Entity com.xulei.likebackend.model.entity.Blog
*/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

}




