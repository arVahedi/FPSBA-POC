package com.db.user.controller;

import com.db.user.assets.ResponseTemplate;
import com.db.user.model.dto.crud.SellerInfoDto;
import com.db.user.model.entity.SellerInfo;
import com.db.user.model.mapper.SellerInfoMapper;
import com.db.user.service.SellerService;
import com.db.lib.dto.GenericDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/sellers")
@Slf4j
@Tag(name = "Seller API", description = "Seller management API")
@RequiredArgsConstructor
@Valid
@PreAuthorize("isAuthenticated()")
public class SellerController extends BaseRestController {

    private final SellerService sellerService;
    private final SellerInfoMapper sellerInfoMapper;

    @Operation(summary = "Activate seller profile", description = "Active an existing user as a seller")
    @PostMapping
    public ResponseEntity<ResponseTemplate<SellerInfoDto>> save(@RequestBody SellerInfoDto sellerInfoDto) {
        log.info("Attempting to active a user as a seller: {}", sellerInfoDto.getUserId());
        SellerInfo seller = this.sellerService.save(sellerInfoDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.sellerInfoMapper.toDto(seller)));
    }

    @Operation(summary = "Update seller info", description = "Updating an exising seller data")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<SellerInfoDto>> update(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                                  @RequestBody @Validated SellerInfoDto request) {
        SellerInfo seller = this.sellerService.update(id, request);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.sellerInfoMapper.toDto(seller)));
    }

    @Operation(summary = "Patch seller", description = "Partial updating an existing seller")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTemplate<SellerInfoDto>> patch(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                                 @RequestBody GenericDto genericDto) {
        SellerInfo seller = this.sellerService.patch(id, genericDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.sellerInfoMapper.toDto(seller)));
    }

    @Operation(summary = "Delete seller", description = "Deleting an exising seller")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        this.sellerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Find seller", description = "Retrieving an exising seller")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<SellerInfoDto>> find(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        SellerInfo seller = this.sellerService.find(id);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.sellerInfoMapper.toDto(seller)));
    }

    @Operation(summary = "Retrieving sellers", description = "Retrieving all exising sellers")
    @GetMapping
    public ResponseEntity<ResponseTemplate<List<SellerInfoDto>>> list(Pageable pageable) {
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.sellerInfoMapper.toDtos(this.sellerService.list(pageable))));
    }
}
