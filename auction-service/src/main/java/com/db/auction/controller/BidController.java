package com.db.auction.controller;

import com.db.auction.assets.ResponseTemplate;
import com.db.auction.model.dto.crud.BidDto;
import com.db.auction.model.mapper.BidMapper;
import com.db.auction.service.BidService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseRestController.API_PATH_PREFIX_V1 + "/bid")
@Slf4j
@Tag(name = "Bid API", description = "Bid management API")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class BidController extends BaseRestController {

    private final BidService bidService;
    private final BidMapper bidMapper;

    @Operation(summary = "Placed a bid", description = "Placed a bid for an existing active auction")
    @PostMapping("/placed")
    public ResponseEntity<ResponseTemplate<Void>> placedBid(@RequestBody @Validated BidDto bidDto) {
        this.bidService.save(bidDto);
        return ResponseEntity.ok(new ResponseTemplate<>(HttpStatus.CREATED));
    }

}
