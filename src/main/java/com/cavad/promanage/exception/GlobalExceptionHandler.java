package com.cavad.promanage.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    public ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
        return buildResponseEntity(
                new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "User Not Found"));
    }
    @ExceptionHandler(value = EmailAlreadyExistException.class)
    public ResponseEntity<Object> emailAlreadyExistException(EmailAlreadyExistException exception) {
        return buildResponseEntity(
                new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT, 409, "An account with the given email already exists"));
    }
    @ExceptionHandler(value = ProjectNotFoundException.class)
    public ResponseEntity<Object> projectNotFoundException(ProjectNotFoundException exception) {
        return buildResponseEntity(
                new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "Project Not Found"));
    }
    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<Object> taskNotFoundException(TaskNotFoundException exception) {
        return buildResponseEntity(
                new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "Task Not Found"));
    }

    @Override
//    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("Timestamp", new Date());
        responseBody.put("Status", status.value());
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        responseBody.put("Errors", errors);
        return new ResponseEntity<>(responseBody, headers, status);
    }
//
//
////    }
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//@ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exception){
//        Map<String,String> errors = new HashMap<>();
//        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {errors.put(fieldError.getField(),fieldError.getDefaultMessage());
//        });
//        return errors;
//}
}