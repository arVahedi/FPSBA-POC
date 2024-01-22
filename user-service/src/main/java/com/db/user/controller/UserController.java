package com.db.user.controller;

import com.db.user.assets.ResponseTemplate;
import com.db.user.model.dto.crud.UserDto;
import com.db.user.model.entity.User;
import com.db.user.model.mapper.UserMapper;
import com.db.user.service.UserService;
import com.db.lib.dto.GenericDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/users")
@Slf4j
@Tag(name = "User API", description = "User management API")
@RequiredArgsConstructor
@Valid
public class UserController extends BaseRestController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Registration user", description = "Registering a new user")
    @PostMapping("/user")
    public ResponseEntity<ResponseTemplate<UserDto>> registerUser(@RequestBody UserDto userDto) {
        log.info("Attempting to register user by email: {}", userDto.getEmail());
        User newUser = this.userService.save(userDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.userMapper.toDto(newUser)));
    }

    @Operation(summary = "Update user", description = "Updating an exising user")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<UserDto>> update(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                            @RequestBody @Validated UserDto request) {
        User user = this.userService.update(id, request);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.userMapper.toDto(user)));
    }

    @Operation(summary = "Patch user", description = "Partial updating an existing user")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTemplate<UserDto>> patch(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                           @RequestBody GenericDto genericDto) {
        User user = this.userService.patch(id, genericDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.userMapper.toDto(user)));
    }

    @Operation(summary = "Delete user", description = "Deleting an exising user")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Find user", description = "Retrieving an exising user")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<UserDto>> find(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        User user = this.userService.find(id);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.userMapper.toDto(user)));
    }

    @Operation(summary = "Retrieving users", description = "Retrieving all exising users")
    @GetMapping
    public ResponseEntity<ResponseTemplate<List<UserDto>>> list(Pageable pageable) {
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.userMapper.toDtos(this.userService.list(pageable))));
    }
}
