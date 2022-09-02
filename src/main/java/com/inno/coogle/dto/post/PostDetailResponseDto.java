package com.inno.coogle.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inno.coogle.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String postTitle;
    private String postContents;
    private String imageUrl;
    private String ingredientsList;
    private String tagList;
    private Long level;
    private String foodType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    @Builder
    public PostDetailResponseDto(Post post) {
        this.postId = post.getId();
        this.nickname = post.getMember().getNickname();
        this.postTitle = post.getTitle();
        this.postContents = post.getContents();
        this.imageUrl = post.getThumbnailUrl();
        this.ingredientsList = post.getIngredientsList();
        this.tagList = post.getTagList();
        this.level = post.getLevel();
        this.foodType = post.getFoodType();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
