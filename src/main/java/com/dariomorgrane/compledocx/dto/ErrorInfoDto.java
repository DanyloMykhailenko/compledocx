package com.dariomorgrane.compledocx.dto;

import java.time.Instant;

public interface ErrorInfoDto {

    Instant timestamp();

    Integer status();

    String error();

    String message();

    String path();

}
