package com.xulei.likebackend.mapper;

import com.xulei.likebackend.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author xl
* @description 针对表【user】的数据库操作Mapper
* @createDate 2026-03-05 22:35:40
* @Entity com.xulei.likebackend.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




