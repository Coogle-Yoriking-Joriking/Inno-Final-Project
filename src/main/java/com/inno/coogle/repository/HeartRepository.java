package com.inno.coogle.repository;

import com.inno.coogle.domain.Heart;
import com.inno.coogle.domain.Member;
import com.inno.coogle.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByMemberAndPost(Member member, Post post);
}
