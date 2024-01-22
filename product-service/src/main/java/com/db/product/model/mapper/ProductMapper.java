package com.db.product.model.mapper;

import com.db.product.configuration.GlobalMapperConfig;
import com.db.product.model.dto.crud.ProductDto;
import com.db.product.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface ProductMapper extends BaseCrudMapper<Product, ProductDto> {


}
