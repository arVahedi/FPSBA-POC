package com.db.auth.model.mapper;

import com.db.auth.assets.Constant;
import com.db.auth.configuration.GlobalMapperConfig;
import com.db.auth.model.dto.crud.UserDto;
import com.db.auth.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = GlobalMapperConfig.class, imports = {Constant.class, StringUtils.class})
public abstract class UserMapper implements BaseCrudMapper<User, UserDto> {

    private PasswordEncoder passwordEncoder;

    @Override
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword", conditionExpression = "java(!Constant.PASSWORD_MASK.equalsIgnoreCase(dto.getPassword()))", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", expression = "java(StringUtils.isNotBlank(dto.getEmail()) ? dto.getEmail().toLowerCase() : dto.getEmail())")
    public abstract User toEntity(UserDto dto);

    @Override
    @Mapping(target = "password", constant = Constant.PASSWORD_MASK)
    public abstract UserDto toDto(User entity);

    @Named("encodePassword")
    public String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
