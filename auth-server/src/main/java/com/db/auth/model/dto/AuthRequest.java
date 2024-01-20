package com.db.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest extends BaseDto {

    @NotBlank(message = "username parameter is required and can not be blank")
    private String username;
    @NotBlank(message = "username parameter is required and can not be blank")
    private String password;
}
