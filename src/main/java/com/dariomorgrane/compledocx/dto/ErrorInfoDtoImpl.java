package com.dariomorgrane.compledocx.dto;

import java.time.Instant;

public record ErrorInfoDtoImpl(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path
) implements ErrorInfoDto {
}
