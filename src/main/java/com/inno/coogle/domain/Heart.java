package com.inno.coogle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Heart {

    @Id
    @Column(name = "heart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Heart(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    public void confirmPost(Post post) {
        this.post = post;
        post.addHearts(this);
    }
}
