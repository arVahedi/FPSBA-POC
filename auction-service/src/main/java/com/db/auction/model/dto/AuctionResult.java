package com.db.auction.model.dto;

import com.db.auction.model.entity.Auction;
import com.db.auction.model.entity.Bid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuctionResult {
    private Auction auction;
    private Bid bestBid;
    private List<Bid> allBids;
}
