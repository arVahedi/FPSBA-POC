package com.db.auction.database.repository.custom;

import com.db.auction.model.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCustomRepository<E extends BaseEntity<?>> {

    @PersistenceContext
    private EntityManager entityManager;

}
