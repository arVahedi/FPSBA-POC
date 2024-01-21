package com.db.auth.service;

import com.db.auth.assets.UserStatus;
import com.db.auth.database.repository.SellerInfoRepository;
import com.db.auth.database.repository.UserRepository;
import com.db.auth.exception.UniqueConstraintAlreadyExistsException;
import com.db.auth.exception.UsernameAlreadyExistsException;
import com.db.auth.model.dto.crud.SellerInfoDto;
import com.db.auth.model.dto.crud.UserDto;
import com.db.auth.model.entity.SellerInfo;
import com.db.auth.model.entity.User;
import com.db.auth.model.mapper.SellerInfoMapper;
import com.db.auth.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService extends BaseService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SellerInfoRepository sellerInfoRepository;
    private final SellerInfoMapper sellerInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    public User registerUser(UserDto userDto) {
        //Prevent duplicate usernames
        checkEmailDuplication(userDto.getEmail(), Optional.empty());
        User userEntity = this.userMapper.toEntity(userDto);
        userEntity.setUid(UUID.randomUUID().toString());
        userEntity.setStatus(UserStatus.ACTIVE);
        return this.userRepository.save(userEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public SellerInfo registerSeller(SellerInfoDto sellerInfoDto) {
        SellerInfo sellerInfo = this.sellerInfoMapper.toEntity(sellerInfoDto);
        this.sellerInfoRepository.findByUser(sellerInfo.getUser()).ifPresent(item -> {
            throw new UniqueConstraintAlreadyExistsException("Seller data is already exist for this user");
        });
        return this.sellerInfoRepository.save(sellerInfo);
    }

    private void checkEmailDuplication(String email, Optional<Long> excludedId) {
        Optional<User> user = this.userRepository.findByEmail((email));
        if (user.isPresent() && (excludedId.isEmpty() || (excludedId.get().longValue() != user.get().getId()))) {
            throw new UsernameAlreadyExistsException();
        }
    }
}
