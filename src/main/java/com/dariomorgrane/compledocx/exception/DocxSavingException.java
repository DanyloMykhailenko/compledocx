package com.dariomorgrane.compledocx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DocxSavingException extends Exception {

    private static final String messageTemplate = "Can not save document %s";

    public DocxSavingException(String documentName, Throwable cause) {
        super(String.format(messageTemplate, documentName), cause);
    }

}
