package com.dariomorgrane.compledocx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DocxLoadingException extends Exception {

    private static final String messageTemplate = "Can not load document %s from bytes.";

    public DocxLoadingException(String documentName, Throwable cause) {
        super(String.format(messageTemplate, documentName), cause);
    }

}
