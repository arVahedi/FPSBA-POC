package com.db.product.service;

import com.db.lib.utility.identity.IdentityUtility;
import com.db.product.database.repository.ProductRepository;
import com.db.product.model.dto.crud.ProductDto;
import com.db.product.model.entity.Product;
import com.db.product.model.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService extends BaseService implements DefaultCrudService<Long, Product, ProductDto> {

    @Getter
    private final ProductRepository repository;
    @Getter
    private final ProductMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product save(ProductDto productDto) {
        Product product = this.mapper.toEntity(productDto);
        product.setSellerUid(IdentityUtility.getUsername().orElseThrow(() -> new AccessDeniedException("Authenticated user is not recognized")));
        return this.repository.save(product);
    }

    public List<Product> retrieveAllProductsOfSeller(String sellerUid) {
        return this.repository.findBySellerUid(sellerUid);
    }
}
