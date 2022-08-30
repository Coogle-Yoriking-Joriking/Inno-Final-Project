package com.inno.coogle.dto.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
    private String postTitle;
    private String postContents;
    private List<String> ingredientsList;
    private List<String> tagList;
    private Long level;
    private String foodType;

}
