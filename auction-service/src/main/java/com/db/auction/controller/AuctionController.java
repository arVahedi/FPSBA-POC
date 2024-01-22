package com.db.auction.controller;

import com.db.auction.assets.ResponseTemplate;
import com.db.auction.model.dto.AuctionResult;
import com.db.auction.model.dto.crud.AuctionDto;
import com.db.auction.model.entity.Auction;
import com.db.auction.model.mapper.AuctionMapper;
import com.db.auction.service.AuctionService;
import com.db.lib.dto.GenericDto;
import com.db.lib.utility.identity.IdentityUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/auctions")
@Slf4j
@Tag(name = "Auction API", description = "Auction management API")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority(T(com.db.lib.assets.AuthorityType).SELLER_AUTHORITY)")
public class AuctionController extends BaseRestController {

    private final AuctionService auctionService;
    private final AuctionMapper auctionMapper;

    @Operation(summary = "Create new auction", description = "Create a new auction")
    @PostMapping
    public ResponseEntity<ResponseTemplate<AuctionDto>> save(@RequestBody @Validated AuctionDto request) {
        Auction auction = this.auctionService.save(request);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED, this.auctionMapper.toDto(auction)));
    }

    @Operation(summary = "Patch auction", description = "Partial updating an existing auction")
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTemplate<AuctionDto>> patch(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id,
                                                              @RequestBody GenericDto genericDto) {
        Auction auction = this.auctionService.patch(id, genericDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.auctionMapper.toDto(auction)));
    }

    @Operation(summary = "Retrieving all auctions of current authenticated seller", description = "Retrieving all auctions of current authenticated seller")
    @GetMapping("/own")
    public ResponseEntity<ResponseTemplate<List<AuctionDto>>> retrieveAllSellerProduct() {
        List<Auction> auctions = this.auctionService.retrieveAllAuctionsOfSeller(IdentityUtility.getUsername().orElseThrow(() -> new AccessDeniedException("Authenticated user is not recognized")));
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.auctionMapper.toDtos(auctions)));
    }

    @Operation(summary = "Delete auction", description = "Deleting an existing auction")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        this.auctionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Retrieving all auctions", description = "Retrieving all auctions")
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<ResponseTemplate<List<AuctionDto>>> list(Pageable pageable) {
        List<Auction> auctions = this.auctionService.list(pageable);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, this.auctionMapper.toDtos(auctions)));
    }

    @Operation(summary = "Stop an existing active auction", description = "Stop an existing active auction")
    @PostMapping("/{id}/stop")
    public ResponseEntity<ResponseTemplate<AuctionResult>> stopAuction(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        AuctionResult auctionResult = this.auctionService.stopAuction(id);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, auctionResult));
    }

    @Operation(summary = "Stop an existing active auction", description = "Stop an existing active auction")
    @GetMapping("/{id}/result")
    public ResponseEntity<ResponseTemplate<AuctionResult>> getResult(@PathVariable @Min(value = 1, message = "Minimum acceptable value for id is 1") long id) {
        AuctionResult auctionResult = this.auctionService.getResult(id);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.OK, auctionResult));
    }

}
