package com.inno.coogle.security;

import com.inno.coogle.domain.Member;
import com.inno.coogle.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        if (findMember != null) {
            return UserDetailsImpl.builder()
                    .username(findMember.getUsername())
                    .password(findMember.getPassword())
                    .nickname(findMember.getNickname())
//                    .img(findMember.getProfile_image())
                    .build();
        }
        return null;
    }
}