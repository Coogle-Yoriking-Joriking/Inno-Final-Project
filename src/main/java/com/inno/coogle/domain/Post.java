package com.inno.coogle.domain;

import com.inno.coogle.converter.StringListConverter;
import com.inno.coogle.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

//    @Convert(converter = StringListConverter.class)
    private String ingredientsList;

//    @Convert(converter = StringListConverter.class)
    private String tagList;
    @NotNull
    private Long level;

    @NotBlank
    private String foodType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    private String thumbnailUrl;

    public Post(Member member, PostRequestDto postRequestDto) {
        this.member = member;
        this.title = postRequestDto.getPostTitle();
        this.contents = postRequestDto.getPostContents();
        this.ingredientsList = postRequestDto.getIngredientsList().toString();
        this.tagList = postRequestDto.getTagList().toString();
        this.level = postRequestDto.getLevel();
        this.foodType = postRequestDto.getFoodType();
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getPostTitle();
        this.contents = postRequestDto.getPostContents();
        this.ingredientsList = postRequestDto.getIngredientsList().toString();
        this.tagList = postRequestDto.getTagList().toString();
        this.level = postRequestDto.getLevel();
        this.foodType = postRequestDto.getFoodType();
    }

}
