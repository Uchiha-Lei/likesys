package com.xulei.likebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.likebackend.model.dto.blog.QryBlogRequest;
import com.xulei.likebackend.model.entity.Blog;
import com.xulei.likebackend.model.entity.Thumb;
import com.xulei.likebackend.model.entity.User;
import com.xulei.likebackend.model.vo.BlogVO;
import com.xulei.likebackend.service.BlogService;
import com.xulei.likebackend.mapper.BlogMapper;
import com.xulei.likebackend.service.ThumbService;
import com.xulei.likebackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
* @author xl
* @description 针对表【blog】的数据库操作Service实现
* @createDate 2026-03-05 22:32:51
*/
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
    implements BlogService{

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private ThumbService thumbService;

    @Override
    public BlogVO getBlogVOById(QryBlogRequest qryBlogRequest, HttpServletRequest request) {
        log.info("*****************************getBlogVOById start*****************************\nqryBlogRequest:{}", qryBlogRequest);
        Blog blog = this.getById(qryBlogRequest.getBlogId());
        User loginUser = userService.getLoginUser(request);
        BlogVO blogVo = getBlogVO(blog, loginUser);
        log.info("*****************************BlogVo={}*****************************\n", blogVo);
        log.info("*****************************getBlogVOById end*****************************\n");
        return blogVo;
    }

//------------------------------------------------------private--------------------------------------------------------
    private BlogVO getBlogVO(Blog blog, User loginUser) {
        BlogVO blogVo = new BlogVO();
        BeanUtil.copyProperties(blog, blogVo);

        if (loginUser == null){
            return blogVo;
        }

        Thumb thumb = thumbService.lambdaQuery()
                .eq(Thumb::getBlogId, blog.getId())
                .eq(Thumb::getUserId, loginUser.getId())
                .one();
        blogVo.setHasThumb(thumb != null);

        return blogVo;
    }
}




