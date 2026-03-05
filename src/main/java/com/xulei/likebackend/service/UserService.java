package com.xulei.likebackend.service;

import com.xulei.likebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author xl
* @description 针对表【user】的数据库操作Service
* @createDate 2026-03-05 22:35:40
*/
public interface UserService extends IService<User> {

    User getLoginUser(HttpServletRequest request);
}
