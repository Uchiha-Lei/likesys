package com.xulei.likebackend.model.dto.blog;

import lombok.Data;

import java.io.Serializable;

@Data
public class QryBlogRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long blogId;
}
