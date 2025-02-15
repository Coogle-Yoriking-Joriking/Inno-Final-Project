package com.inno.coogle.service;

import com.inno.coogle.domain.Member;
import com.inno.coogle.dto.member.LoginRequestDto;
import com.inno.coogle.dto.member.LoginResponseDto;
import com.inno.coogle.dto.member.SignupRequestDto;
import com.inno.coogle.global.error.exception.ErrorCode;
import com.inno.coogle.global.error.exception.InvalidValueException;
import com.inno.coogle.jwt.JwtTokenProvider;
import com.inno.coogle.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository userRepository;

//    private final S3Service s3Service;

    public void registerUser(SignupRequestDto requestDto/*, MultipartFile filePath*/) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String nickname = requestDto.getNickname();
//        String img = requestDto.getImg();
        String pattern = "^[a-zA-Z0-9]*$";

        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new InvalidValueException(ErrorCode.USERNAME_DUPLICATION);
        }

//        String img = "";
//        if (filePath != null) {
//            img = s3Service.uploadImg(filePath);
//        }

        if (username.length() < 4) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_USERNAME);
        } else if (!Pattern.matches(pattern, username)) {
            throw new InvalidValueException(ErrorCode.INVALID_USERNAME);
        } else if (!password.equals(passwordCheck)) {
            throw new InvalidValueException(ErrorCode.NOTEQUAL_INPUT_PASSWORD);
        } else if (password.length() < 4) {
            throw new InvalidValueException(ErrorCode.INVALID_PASSWORD);
        } else if (nickname.length() < 2) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_NICKNAME);
        }

        userRepository.save(new Member(username, passwordEncoder.encode(password), nickname)/*,img*/);
    }

    public String login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        Member member = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOTFOUND_USER.getMessage()));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new InvalidValueException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        return jwtTokenProvider.createToken(username);
    }

    //현재 SecurityContext에 있는 유저 정보 가져오기기
    @Transactional
    public LoginResponseDto getMyInfo() {
        // SecurityContext 에 유저 정보가 저장되는 시점
        // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장

        //JwtFilter 에서 SecurityContext 에 세팅한 유저 정보를 꺼냅니다.
        //유저네임으로 찾음 -> 코멘트/리코멘트 생성 시에 사제
        //SecurityContext 는 ThreadLocal 에 사용자의 정보를 저장합니다.

        //저장된 유저 정보
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //유저 정보 있는지 확인
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다");
        }
        //토큰에서 유저 네임으로 찾은 유저 정보 정보 중 Dto에 해당하는 필드 가져오기 (여기선 유저 네임)
        return userRepository.findByUsername(authentication.getName())
                .map(LoginResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }
}