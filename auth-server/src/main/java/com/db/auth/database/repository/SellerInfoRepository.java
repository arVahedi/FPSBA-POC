package com.db.auth.database.repository;

import com.db.auth.model.entity.SellerInfo;
import com.db.auth.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface SellerInfoRepository extends BaseRepository<SellerInfo, Long> {

    Optional<SellerInfo> findByUser(User user);
}
