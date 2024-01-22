package com.db.product.database.repository;

import com.db.product.model.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ProductRepository extends BaseRepository<Product, Long> {

    List<Product> findBySellerUid(String sellerUid);
}
