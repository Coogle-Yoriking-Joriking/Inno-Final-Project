package com.inno.coogle.controller;

import com.inno.coogle.global.common.response.ApiUtils;
import com.inno.coogle.global.common.response.CommonResponse;
import com.inno.coogle.global.error.exception.ErrorCode;
import com.inno.coogle.global.error.exception.InvalidValueException;
import com.inno.coogle.security.UserDetailsImpl;
import com.inno.coogle.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/{postId}")
    public CommonResponse<?> hearts(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new InvalidValueException(ErrorCode.HANDLE_ACCESS_DENIED);
        }
        String username = userDetails.getUsername();
        return ApiUtils.success(200, heartService.hearts(username, postId));
    }
}
