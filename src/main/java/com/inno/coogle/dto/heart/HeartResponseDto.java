package com.inno.coogle.dto.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class HeartResponseDto {
    private int heartNum;
    private Boolean heartState;
}
