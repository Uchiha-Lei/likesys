package com.xulei.likebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.likebackend.constant.UserConstant;
import com.xulei.likebackend.model.entity.User;
import com.xulei.likebackend.service.UserService;
import com.xulei.likebackend.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author xl
* @description 针对表【user】的数据库操作Service实现
* @createDate 2026-03-05 22:35:40
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(UserConstant.LOGIN_USER);
    }

}




