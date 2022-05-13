package com.dariomorgrane.compledocx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFoundException extends RuntimeException {

    private static final String MESSAGE_TEMPLATE = "Supplemented document with id %d not found";

    public DocumentNotFoundException(Long documentId) {
        super(String.format(MESSAGE_TEMPLATE, documentId));
    }

}
