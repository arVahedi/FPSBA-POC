package com.db.auction.model.entity;

import com.db.auction.assets.AuctionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.Instant;

@Entity
@Table(name = "auction")
@Where(clause = "deleted=false")
@Getter
@Setter
public class Auction extends LogicalDeletableEntity<Long> {

    @Basic
    @Column(name = "product_id")
    private long productId;
    @Basic
    @Column(name = "minimum_price")
    private int minimumPrice;
    @Basic
    @Column(name = "seller_id")
    private String sellerId;
    @Column(name = "status")
    @Convert(converter = AuctionStatus.Converter.class)
    private AuctionStatus status;
}
