package com.db.user.service;

import com.db.user.assets.UserStatus;
import com.db.user.database.repository.UserRepository;
import com.db.user.model.dto.crud.UserDto;
import com.db.user.model.entity.User;
import com.db.user.model.mapper.UserMapper;
import com.db.lib.exception.UsernameAlreadyExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends BaseService implements DefaultCrudService<Long, User, UserDto> {

    @Getter
    private final UserRepository repository;
    @Getter
    private final UserMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(UserDto userDto) {
        //Prevent duplicate usernames
        checkEmailDuplication(userDto.getEmail(), Optional.empty());
        User userEntity = this.mapper.toEntity(userDto);
        userEntity.setUid(UUID.randomUUID().toString());
        userEntity.setStatus(UserStatus.ACTIVE);
        return this.repository.save(userEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User update(Long id, UserDto userDto) {
        //Prevent duplicate usernames
        checkEmailDuplication(userDto.getEmail(), Optional.of(id));
        return DefaultCrudService.super.update(id, userDto);
    }

    private void checkEmailDuplication(String email, Optional<Long> excludedId) {
        Optional<User> user = this.repository.findByEmail((email));
        if (user.isPresent() && (excludedId.isEmpty() || (excludedId.get().longValue() != user.get().getId()))) {
            throw new UsernameAlreadyExistsException();
        }
    }
}
