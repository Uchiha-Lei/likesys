package com.xulei.likebackend.service;

import com.xulei.likebackend.model.dto.blog.QryBlogRequest;
import com.xulei.likebackend.model.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xulei.likebackend.model.vo.BlogVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author xl
* @description 针对表【blog】的数据库操作Service
* @createDate 2026-03-05 22:32:51
*/
public interface BlogService extends IService<Blog> {

    /**
     * 根据id查询帖子
     * @param qryBlogRequest 查询对象
     * @param request
     * @return
     */
    BlogVO getBlogVOById(QryBlogRequest qryBlogRequest, HttpServletRequest request);

    /**
     * 查询帖子列表
     * @param blogList
     * @param request
     * @return
     */
    List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request);


}
