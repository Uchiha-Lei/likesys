package com.xulei.likebackend.model.dto.thum;

import lombok.Data;

import java.io.Serializable;

/**
 * 做点赞
 */
@Data
public class DoThumbRequest {

    /**
     * 帖子id
     */
    private Long blogId;
}
