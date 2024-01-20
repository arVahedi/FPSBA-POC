package com.db.auth.controller;

import com.db.auth.assets.ResponseTemplate;
import com.db.auth.model.dto.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/register")
@Slf4j
@Tag(name = "Registration API", description = "Authentication API")
@RequiredArgsConstructor
@Valid
public class RegistrationController extends BaseRestController {

    public ResponseEntity<ResponseTemplate<Void>> registerUser() {

    }
}
