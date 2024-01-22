package com.db.user.service;

import com.db.user.database.repository.UserRepository;
import com.db.user.model.dto.crud.UserDto;
import com.db.user.model.entity.User;
import com.db.user.model.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;
    @SpyBean
    private UserMapper userMapper;
    @SpyBean
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(this.userService, "mapper", this.userMapper);
    }

    @Test
    void createUserTest() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@db.com");
        userDto.setPassword("123456");

        this.userService.save(userDto);
        Mockito.verify(this.userRepository).save(any(User.class));
    }

    @Test
    void whenUserIsCreatedThenUUIDShouldBeGenerated() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@db.com");
        userDto.setPassword("123456");

        Mockito.when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.userService.save(userDto);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(this.userRepository).save(userArgumentCaptor.capture());

        Assertions.assertNotNull(userArgumentCaptor.getValue().getUid());
    }

    @Test
    void saveHashedPasswordTest() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@db.com");
        userDto.setPassword("123456");

        Mockito.when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        this.userService.save(userDto);
        Mockito.verify(this.passwordEncoder).encode(anyString());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(this.userRepository).save(userArgumentCaptor.capture());
        Assertions.assertTrue(this.passwordEncoder.matches(userDto.getPassword(), userArgumentCaptor.getValue().getPassword()));
    }
}