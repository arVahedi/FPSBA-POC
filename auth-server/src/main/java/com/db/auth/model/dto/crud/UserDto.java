package com.db.auth.model.dto.crud;

import com.db.auth.assets.Regex;
import com.db.auth.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseCrudDto<User, Long> {
    @NotBlank(message = "firstName is mandatory")
    private String firstName;
    @NotBlank(message = "lastName is mandatory")
    private String lastName;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotBlank(message = "email is mandatory")
    @Pattern(regexp = Regex.EMAIL, message = "Email format is wrong")
    private String email;
}
