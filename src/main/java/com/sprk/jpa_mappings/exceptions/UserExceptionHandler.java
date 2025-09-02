package com.sprk.jpa_mappings.exceptions;

import com.sprk.jpa_mappings.dtos.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();

//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        APIResponse apiResponse = APIResponse.builder()
           .error(errorMessage)
           .build();

        return ResponseEntity
           .status(HttpStatus.BAD_REQUEST)
           .body(apiResponse);
    }

    @ExceptionHandler(DuplicateDataFoundException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(DuplicateDataFoundException ex) {

        APIResponse<?> apiResponse = APIResponse.builder()
           .error(ex.getMessage())
           .build();

        return ResponseEntity
           .status(HttpStatus.CONFLICT)
           .body(apiResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex){
        return ResponseEntity
           .status(HttpStatus.NOT_FOUND)
           .body(
              APIResponse.builder()
                 .error(ex.getMessage())
                 .build()
           );
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleException(Exception e) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//    }
}
