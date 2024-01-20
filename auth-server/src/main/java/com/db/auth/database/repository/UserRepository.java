package com.db.auth.database.repository;

import com.db.auth.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User, Long> {

    @Query("select u from User u where u.email = :username or u.uid = :username")
    Optional<User> findByEmailOrUid(String username);
}
