package com.morka.bank.controller;

import com.morka.bank.exception.EmailExistsException;
import com.morka.bank.exception.PassportIdExistsException;
import com.morka.bank.exception.PassportNumberExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage(), ErrorCode.NOT_FOUND.getCodeName()));
    }

    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), ErrorCode.EMAIL_EXISTS.getCodeName()));
    }

    @ExceptionHandler(PassportIdExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePassportExists(PassportIdExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), ErrorCode.PASSPORT_ID_EXISTS.getCodeName()));
    }

    @ExceptionHandler(PassportNumberExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePassportNumberExists(PassportNumberExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage(), ErrorCode.PASSPORT_NUMBER_EXISTS.getCodeName()));
    }

    private record ErrorResponse(String message, String code) {
    }

    private enum ErrorCode {
        NOT_FOUND("not_found"),
        EMAIL_EXISTS("email_exists"),
        PASSPORT_ID_EXISTS("passport_id_exists"),
        PASSPORT_NUMBER_EXISTS("passport_number_exists");

        private final String codeName;

        ErrorCode(String code) {
            this.codeName = code;
        }

        public String getCodeName() {
            return codeName;
        }
    }
}
