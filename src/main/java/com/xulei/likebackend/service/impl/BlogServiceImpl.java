package com.xulei.likebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xl
 * @description 针对表【blog】的数据库操作Service实现
 * @createDate 2026-03-05 22:32:51
 */
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>
        implements BlogService {

    @Resource
    private UserService userService;

    @Resource
    @Lazy
    private ThumbService thumbService;

    @Override
    public BlogVO getBlogVOById(QryBlogRequest qryBlogRequest, HttpServletRequest request) {
        log.info("*****************************getBlogVOById start*****************************\nqryBlogRequest:{}", qryBlogRequest.toString());
        Blog blog = this.getById(qryBlogRequest.getBlogId());
        User loginUser = userService.getLoginUser(request);
        BlogVO blogVo = getBlogVO(blog, loginUser);
        log.info("*****************************BlogVo={}*****************************\n", blogVo.toString());
        log.info("*****************************getBlogVOById end*****************************\n");
        return blogVo;
    }

    @Override
    public List<BlogVO> getBlogVOList(List<Blog> blogList, HttpServletRequest request) {
        log.info("*****************************getBlogVOList start*****************************\nblogList:{}", blogList.toString());
        User loginUser = userService.getLoginUser(request);
        Map<Long, Boolean> blogIdHasThumMap = new HashMap<>();
        if (ObjectUtil.isNotEmpty(loginUser)) {
            Set<Long> blogIdSet = blogList.stream().map(Blog::getId).collect(Collectors.toSet());
            // 获取点赞
            List<Thumb> thumbList = thumbService.lambdaQuery()
                    .eq(Thumb::getUserId, loginUser.getId())
                    .eq(Thumb::getBlogId, blogIdSet)
                    .list();

            thumbList.forEach(blogThumb -> {
                blogIdHasThumMap.put(blogThumb.getBlogId(), true);
            });
        }
        log.info("*****************************getBlogVOList end*****************************\n");
        return blogList.stream()
                .map(blog -> {
                    BlogVO blogVo = BeanUtil.copyProperties(blog, BlogVO.class);
                    blogVo.setHasThumb(blogIdHasThumMap.get(blog.getId()));
                    return blogVo;
                }).toList();
    }


    //------------------------------------------------------private--------------------------------------------------------
    private BlogVO getBlogVO(Blog blog, User loginUser) {
        BlogVO blogVo = new BlogVO();
        BeanUtil.copyProperties(blog, blogVo);

        if (loginUser == null) {
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




