package com.db.product.model.dto.crud;

import com.db.product.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseCrudDto<Product, Long> {

    private String name;
    private String description;
    private String sellerUid;
}
