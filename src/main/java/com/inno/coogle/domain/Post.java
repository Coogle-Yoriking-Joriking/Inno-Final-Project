package com.inno.coogle.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inno.coogle.converter.StringListConverter;
import com.inno.coogle.dto.post.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    @NotBlank
    private String ingredientsList;

    @NotBlank
    private String tagList;

    @NotNull
    private int level;

    @NotBlank
    private String foodType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();

    private String thumbnailUrl;

    private int heartNum;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Heart> hearts = new LinkedList<>();

    public Post(Member member, PostRequestDto postRequestDto) {
        this.member = member;
        this.title = postRequestDto.getPostTitle();
        this.contents = postRequestDto.getPostContents();
        this.ingredientsList = postRequestDto.getIngredientsList().toString();
        this.tagList = postRequestDto.getTagList().toString();
        this.level = postRequestDto.getLevel();
        this.foodType = postRequestDto.getFoodType();
        this.heartNum = 0;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getPostTitle();
        this.contents = postRequestDto.getPostContents();
        this.ingredientsList = postRequestDto.getIngredientsList().toString();
        this.tagList = postRequestDto.getTagList().toString();
        this.level = postRequestDto.getLevel();
        this.foodType = postRequestDto.getFoodType();
    }

    public void addHearts(Heart heart) {
        hearts.add(heart);
    }

    public void like() {
        this.heartNum += 1;
    }

    public void dislike() {
        this.heartNum -= 1;
    }

}
