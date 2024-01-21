package com.db.auth.controller.advisor;


import com.db.auth.assets.ErrorCode;
import com.db.auth.assets.ResponseTemplate;
import com.db.auth.exception.ObjectMappingException;
import com.db.auth.exception.UniqueConstraintAlreadyExistsException;
import com.db.auth.exception.UsernameAlreadyExistsException;
import com.db.auth.utility.identity.IdentityUtility;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StaleObjectStateException.class, ObjectOptimisticLockingFailureException.class, EntityNotFoundException.class})
    public ResponseEntity<ResponseTemplate<String>> handleExpiredDataException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ResponseTemplate<String> responseTemplate = new ResponseTemplate<>(ErrorCode.EXPIRED_DATA, "Row was updated or deleted by another transaction");
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseTemplate<List<String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        List<String> messages = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        ResponseTemplate<List<String>> responseTemplate = new ResponseTemplate<>(HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueConstraintAlreadyExistsException.class)
    public ResponseEntity<ResponseTemplate<String>> handleConstraintViolationException(UniqueConstraintAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        ResponseTemplate<String> responseTemplate = new ResponseTemplate<>(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectMappingException.class)
    public ResponseEntity<ResponseTemplate<String>> handleConstraintViolationException(ObjectMappingException ex) {
        log.error(ex.getMessage(), ex);
        ResponseTemplate<String> responseTemplate = new ResponseTemplate<>(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseTemplate<String>> handleAccessDeniedException(AccessDeniedException ex) {
        log.error(ex.getMessage(), ex);
        HttpStatus status = IdentityUtility.isAuthenticated() ? HttpStatus.FORBIDDEN : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).build();
    }

    @ExceptionHandler({BadCredentialsException.class, InvalidBearerTokenException.class, JwtValidationException.class, AuthenticationServiceException.class})
    public ResponseEntity<ResponseTemplate<String>> handleBadCredentialsException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ResponseTemplate<String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseTemplate<>(HttpStatus.CONFLICT, "Username already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ResponseTemplate<String>> handleGeneralException(PropertyReferenceException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseTemplate<>(HttpStatus.BAD_REQUEST, "No property found"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ResponseTemplate<String>> handleGeneralException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseTemplate<>(HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> messages.add(fieldError.getDefaultMessage()));
        ResponseTemplate<List<String>> responseTemplate = new ResponseTemplate<>(HttpStatus.BAD_REQUEST, messages);
        return new ResponseEntity<>(responseTemplate, HttpStatus.BAD_REQUEST);
    }
}
