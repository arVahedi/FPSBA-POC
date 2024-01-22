package com.db.user.service;

import com.db.user.database.repository.SellerInfoRepository;
import com.db.user.model.dto.crud.SellerInfoDto;
import com.db.user.model.entity.SellerInfo;
import com.db.user.model.mapper.SellerInfoMapper;
import com.db.lib.exception.UniqueConstraintAlreadyExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService extends BaseService implements DefaultCrudService<Long, SellerInfo, SellerInfoDto> {

    @Getter
    private final SellerInfoRepository repository;
    @Getter
    private final SellerInfoMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SellerInfo save(SellerInfoDto sellerInfoDto) {
        SellerInfo sellerInfo = this.mapper.toEntity(sellerInfoDto);
        this.repository.findByUser(sellerInfo.getUser()).ifPresent(item -> {
            throw new UniqueConstraintAlreadyExistsException("Seller data is already exist for this user");
        });
        return DefaultCrudService.super.save(sellerInfoDto);
    }
}
