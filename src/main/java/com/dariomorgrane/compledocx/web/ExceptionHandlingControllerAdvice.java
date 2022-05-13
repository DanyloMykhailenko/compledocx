package com.dariomorgrane.compledocx.web;

import com.dariomorgrane.compledocx.dto.ErrorInfoDto;
import com.dariomorgrane.compledocx.dto.ErrorInfoDtoImpl;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorInfoDto handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        return new ErrorInfoDtoImpl(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                exception.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collect(Collectors.joining(", ")),
                request.getServletPath()
        );
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ErrorInfoDto handleMethodArgumentNotValid(ConstraintViolationException exception, HttpServletRequest request) {
        return new ErrorInfoDtoImpl(
                Instant.now(),
                BAD_REQUEST.value(),
                BAD_REQUEST.getReasonPhrase(),
                exception.getConstraintViolations().stream()
                        .map(this::getViolationStringRepresentation)
                        .collect(Collectors.joining(", ")),
                request.getServletPath()
        );
    }

    private String getViolationStringRepresentation(ConstraintViolation<?> violation) {
        Deque<Path.Node> nodesStack = new ArrayDeque<>();
        violation.getPropertyPath().forEach(nodesStack::addFirst);
        return nodesStack.getFirst() + " " + violation.getMessage();
    }

}
