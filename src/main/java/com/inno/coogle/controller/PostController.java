package com.inno.coogle.controller;

import com.inno.coogle.domain.Member;
import com.inno.coogle.dto.post.PostRequestDto;
import com.inno.coogle.dto.post.PostResponseDto;
import com.inno.coogle.global.common.response.ApiUtils;
import com.inno.coogle.global.common.response.CommonResponse;
import com.inno.coogle.global.error.exception.ErrorCode;
import com.inno.coogle.global.error.exception.InvalidValueException;
import com.inno.coogle.repository.MemberRepository;
import com.inno.coogle.security.UserDetailsImpl;
import com.inno.coogle.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<?> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestPart PostRequestDto postRequestDto,
                                        @RequestPart(required = false) List<MultipartFile> imageFileList) {

        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }
        Member member = memberRepository.findMemberByUsername(userDetails.getUsername());
        postService.createPost(postRequestDto, member, imageFileList);
        return ApiUtils.success(200, "게시글이 등록되었습니다.");
    }

    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }
}
