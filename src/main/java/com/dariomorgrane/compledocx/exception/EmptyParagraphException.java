package com.dariomorgrane.compledocx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyParagraphException extends Exception {

    public EmptyParagraphException(String message) {
        super(message);
    }

}
