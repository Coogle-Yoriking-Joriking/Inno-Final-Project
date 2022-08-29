package com.inno.coogle.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long memberId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

//    String profile_image;


    public Member(String username, String password, String nickname/*, String img*/) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
//        this.profile_image = img;
    }
}