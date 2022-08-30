package com.inno.coogle.dto.post;

import com.inno.coogle.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postId;
    private String postTitle;
    private String imageUrl;

    @Builder
    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.imageUrl = post.getImageList().get(0).getImageUrl();
    }

}
