package com.inno.coogle.service;

import com.inno.coogle.domain.Heart;
import com.inno.coogle.domain.Member;
import com.inno.coogle.domain.Post;
import com.inno.coogle.dto.heart.HeartResponseDto;
import com.inno.coogle.global.error.exception.EntityNotFoundException;
import com.inno.coogle.global.error.exception.ErrorCode;
import com.inno.coogle.global.error.exception.InvalidValueException;
import com.inno.coogle.repository.HeartRepository;
import com.inno.coogle.repository.MemberRepository;
import com.inno.coogle.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    @Transactional
    public HeartResponseDto hearts(String username, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.NOTFOUND_POST));
        Member member = memberRepository.findMemberByUsername(username);

        Boolean heartState;
        Heart heart = heartRepository.findByMemberAndPost(member, post);

        if (post.getHearts().contains(heart)) {
            heartRepository.deleteById(heart.getId());
            post.dislike();
            post.getHearts().remove(heart);
            heartState = false;
        } else {
            post.like();
            heart = new Heart(member, post);
            heartRepository.save(heart);
            heart.confirmPost(post);
            postRepository.save(post);
            heartState = true;
        }
        return HeartResponseDto.builder()
                .heartState(heartState)
                .heartNum(post.getHeartNum())
                .build();
    }

    public Boolean heartState(Post post, Member member) {
        Heart heart = heartRepository.findByMemberAndPost(member, post);
        return heart != null;
    }
}
