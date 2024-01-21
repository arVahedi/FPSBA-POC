package com.db.auth.controller;

import com.db.auth.assets.ResponseTemplate;
import com.db.auth.model.dto.crud.SellerInfoDto;
import com.db.auth.model.dto.crud.UserDto;
import com.db.auth.model.entity.SellerInfo;
import com.db.auth.model.entity.User;
import com.db.auth.model.mapper.SellerInfoMapper;
import com.db.auth.model.mapper.UserMapper;
import com.db.auth.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/register")
@Slf4j
@Tag(name = "Registration API", description = "Authentication API")
@RequiredArgsConstructor
@Valid
//@PreAuthorize("hasAuthority(T(com.db.auth.assets.AuthorityType).SELLER_AUTHORITY)")
public class RegistrationController extends BaseRestController {

    private final RegistrationService registrationService;
    private final UserMapper userMapper;
    private final SellerInfoMapper sellerInfoMapper;

    @Operation(summary = "Registration user", description = "Registering a new user")
    @PostMapping("/user")
    public ResponseEntity<ResponseTemplate<UserDto>> registerUser(@RequestBody UserDto userDto) {
        log.info("Attempting to register user by email: {}", userDto.getEmail());
        User newUser = this.registrationService.registerUser(userDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.userMapper.toDto(newUser)));
    }

    @Operation(summary = "Activate seller profile", description = "Active an existing user as a seller")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/seller")
    public ResponseEntity<ResponseTemplate<SellerInfoDto>> registerUser(@RequestBody SellerInfoDto sellerInfoDto) {
        log.info("Attempting to active a user as a seller: {}", sellerInfoDto.getUserId());
        SellerInfo newSeller = this.registrationService.registerSeller(sellerInfoDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.sellerInfoMapper.toDto(newSeller)));
    }
}
