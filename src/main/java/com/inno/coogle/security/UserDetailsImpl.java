package com.inno.coogle.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private String username;

    private String password;

    private String nickname;
    String profile_image;

    @Builder
    public UserDetailsImpl(String username, String nickname, String password, String img) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.profile_image = img;
    }

    @Override // 인가를 해주는 부분
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile_image() {
        return profile_image;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨 있는지 잠겨 있지 않았는지 리턴함 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정이 만료되지 않았는지 리턴함 (true: 만료 안 됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용 가능)인지 리턴함 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

}