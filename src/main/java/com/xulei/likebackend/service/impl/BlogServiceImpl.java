package com.xulei.likebackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xulei.likebackend.constant.ThumbConstant;
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
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private RedisTemplate redisTemplate;

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
        Map<Long, Boolean> blogIdHasThumbMap = new HashMap<>();

        if (ObjUtil.isNotEmpty(loginUser)) {
            List<Object> blogIdList = blogList.stream().map(blog -> blog.getId().toString()).collect(Collectors.toList());
            // 获取点赞
            List<Object> thumbList = redisTemplate.opsForHash().multiGet(ThumbConstant.USER_THUMB_KEY_PREFIX + loginUser.getId(), blogIdList);
            for (int i = 0; i < thumbList.size(); i++) {
                if (thumbList.get(i) == null) {
                    continue;
                }
                blogIdHasThumbMap.put(Long.valueOf(blogIdList.get(i).toString()), true);
            }
        }
        log.info("*****************************getBlogVOList end*****************************\n");
        return blogList.stream()
                .map(blog -> {
                    BlogVO blogVo = BeanUtil.copyProperties(blog, BlogVO.class);
                    blogVo.setHasThumb(blogIdHasThumbMap.get(blog.getId()));
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

        Boolean exist = thumbService.hasThumb(blog.getId(), loginUser.getId());
        blogVo.setHasThumb(exist);


        return blogVo;
    }
}




