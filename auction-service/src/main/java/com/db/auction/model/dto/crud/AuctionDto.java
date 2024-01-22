package com.db.auction.model.dto.crud;

import com.db.auction.assets.AuctionStatus;
import com.db.auction.model.entity.Auction;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AuctionDto extends BaseCrudDto<Auction, Long> {
    private long productId;
    private int minimumPrice;
    private String sellerId;
    private AuctionStatus status;
}
