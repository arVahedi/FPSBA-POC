package com.db.auction.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "bid")
@Where(clause = "deleted=false")
@Getter
@Setter
public class Bid extends LogicalDeletableEntity<Long> {

    @Basic
    @Column(name = "user_uid")
    private String userUid;
    @Basic
    @Column(name = "price")
    private int price;
    @OneToOne(targetEntity = Auction.class, fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    private Auction auction;
}
