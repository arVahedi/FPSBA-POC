package com.db.user.database.repository;

import com.db.user.model.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmail(String username);
}
