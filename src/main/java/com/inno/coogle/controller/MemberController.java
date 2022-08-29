package com.inno.coogle.controller;

import com.inno.coogle.dto.member.LoginRequestDto;
import com.inno.coogle.dto.member.SignupRequestDto;
import com.inno.coogle.global.common.response.ApiUtils;
import com.inno.coogle.global.common.response.CommonResponse;
import com.inno.coogle.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "signup"/*,consumes = {MediaType.APPLICATION_JSON_VALUE/*, MediaType.MULTIPART_FORM_DATA_VALUE}*/)
    public CommonResponse<?> registerMember(@RequestBody @Valid SignupRequestDto requestDto/*,
                                            @RequestPart(required = false) MultipartFile img*/) {
        memberService.registerUser(requestDto/*,img*/);
        return ApiUtils.success(201, "회원가입에 성공하였습니다.");
    }

    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        String token = memberService.login(loginRequestDto);
        return ApiUtils.success(200, token);
    }

}