package com.db.auth.controller;

import com.db.auth.assets.ResponseTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/register")
@Slf4j
@Tag(name = "Registration API", description = "Authentication API")
@RequiredArgsConstructor
@Valid
@PreAuthorize("hasAuthority(T(com.db.auth.assets.AuthorityType).SELLER_AUTHORITY)")
public class RegistrationController extends BaseRestController {


    @PostMapping("/user")
    public ResponseEntity<ResponseTemplate<Void>> registerUser() {
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.ACCEPTED));
    }
}
