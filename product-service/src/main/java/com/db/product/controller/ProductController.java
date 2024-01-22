package com.db.product.controller;

import com.db.lib.dto.GenericDto;
import com.db.lib.utility.identity.IdentityUtility;
import com.db.product.assets.ResponseTemplate;
import com.db.product.model.dto.crud.ProductDto;
import com.db.product.model.entity.Product;
import com.db.product.model.mapper.ProductMapper;
import com.db.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/products")
@Slf4j
@Tag(name = "Product API", description = "Product management API")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority(T(com.db.lib.assets.AuthorityType).SELLER_AUTHORITY)")
public class ProductController extends BaseRestController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Create new product", description = "Create a new product")
    @PostMapping
    public ResponseEntity<ResponseTemplate<ProductDto>> save(@RequestBody @Validated ProductDto request) {
        Product product = this.productService.save(request);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.productMapper.toDto(product)));
    }

    @Operation(summary = "Patch product", description = "Partial updating an existing product")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ProductDto>> patch(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                              @RequestBody GenericDto genericDto) {
        Product product = this.productService.patch(id, genericDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.productMapper.toDto(product)));
    }

    @Operation(summary = "Retrieving all products of current authenticated seller", description = "Retrieving all products of current authenticated seller")
    @GetMapping
    public ResponseEntity<ResponseTemplate<List<ProductDto>>> retrieveAllSellerProduct() {
        List<Product> products = this.productService.retrieveAllProductsOfSeller(IdentityUtility.getUsername().orElseThrow(() -> new AccessDeniedException("Authenticated user is not recognized")));
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.productMapper.toDtos(products)));
    }

    @Operation(summary = "Delete product", description = "Deleting an existing product")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
