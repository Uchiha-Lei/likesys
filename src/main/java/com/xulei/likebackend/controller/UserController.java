package com.xulei.likebackend.controller;

import com.xulei.likebackend.common.BaseResponse;
import com.xulei.likebackend.common.ResultUtils;
import com.xulei.likebackend.constant.UserConstant;
import com.xulei.likebackend.exception.ErrorCode;
import com.xulei.likebackend.exception.ThrowUtils;
import com.xulei.likebackend.model.dto.user.UserLoginRequest;
import com.xulei.likebackend.model.entity.User;
import com.xulei.likebackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户id
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        log.info("********login start***********\nrequest={}", request);
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR, "请求参数不能为空");
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(userLoginRequest.getUserId());
        log.info("****************************\nuser={}", user);
        request.getSession().setAttribute(UserConstant.LOGIN_USER, user);
        log.info("********login end***********");
        return ResultUtils.success(user);
    }

    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("/get/login")
    public BaseResponse<User> getLoginUser(HttpServletRequest request) {
        log.info("********getLoginUser start***********\nrequest={}", request);
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        User loginUser = (User) request.getSession().getAttribute(UserConstant.LOGIN_USER);
        log.info("****************************\nloginUser={}", loginUser);
        log.info("********getLoginUser end***********");
        return ResultUtils.success(loginUser);
    }

}
