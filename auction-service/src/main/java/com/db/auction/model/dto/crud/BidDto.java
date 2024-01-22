package com.db.auction.model.dto.crud;

import com.db.auction.model.entity.Bid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidDto extends BaseCrudDto<Bid, Long> {
    private long userUid;
    private int auctionId;
    private int price;
}
