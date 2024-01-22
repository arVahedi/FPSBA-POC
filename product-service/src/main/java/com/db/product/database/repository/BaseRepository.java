package com.db.product.database.repository;

import com.db.product.model.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity<I>, I extends Number> extends JpaRepository<E, I> {

    void detach(E entity);
}
