package com.db.auth.controller;

import com.db.auth.assets.ResponseTemplate;
import com.db.auth.model.dto.AuthRequest;
import com.db.auth.model.dto.AuthResponse;
import com.db.auth.service.UsernamePasswordAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.lang.JoseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/authenticate")
@Slf4j
@Tag(name = "Authentication API", description = "Authentication API")
@RequiredArgsConstructor
@Valid
public class AuthenticationController extends BaseRestController {

    public final UsernamePasswordAuthenticationService authenticationService;

    @Operation(summary = "Authenticating user", description = "Authenticating an existing user via username and password")
    @PostMapping("/token")
    public ResponseEntity<ResponseTemplate<AuthResponse>> authenticate(HttpServletRequest request, HttpServletResponse response, @RequestBody @Validated AuthRequest authRequest) throws ServletException, IOException, OperationNotSupportedException, JoseException {
        log.info("Received authentication request with username [{}]", authRequest.getUsername());
        String token = this.authenticationService.authenticate(request, response, authRequest);
        return ResponseEntity.ok()
                .body(new ResponseTemplate<>(HttpStatus.OK,
                        AuthResponse.builder()
                                .token(token)
                                .build()));
    }
}
