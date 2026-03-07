package com.xulei.likebackend.controller;

import com.xulei.likebackend.common.BaseResponse;
import com.xulei.likebackend.common.ResultUtils;
import com.xulei.likebackend.exception.ErrorCode;
import com.xulei.likebackend.exception.ThrowUtils;
import com.xulei.likebackend.model.dto.blog.QryBlogRequest;
import com.xulei.likebackend.model.entity.Blog;
import com.xulei.likebackend.model.vo.BlogVO;
import com.xulei.likebackend.service.BlogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    /**
     * 获取帖子ById
     *
     * @param qryBlogRequest
     * @param request
     * @return
     */
    @PostMapping("/get/blog")
    public BaseResponse<BlogVO> getBlogById(@RequestBody QryBlogRequest qryBlogRequest, HttpServletRequest request) {
        log.info("********getBlogById start***********\nqryBlogRequest={}", qryBlogRequest.toString());
        ThrowUtils.throwIf(qryBlogRequest == null, ErrorCode.PARAMS_ERROR);
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        BlogVO blogVO = blogService.getBlogVOById(qryBlogRequest, request);
        log.info("********getBlogById end************\nblogVO={}", blogVO.toString());
        return ResultUtils.success(blogVO);
    }

    @PostMapping("/list/blog")
    public BaseResponse<List<BlogVO>> list(HttpServletRequest request) {
        List<Blog> blogList = blogService.list();
        List<BlogVO> blogVOList = blogService.getBlogVOList(blogList, request);
        return ResultUtils.success(blogVOList);
    }

}
