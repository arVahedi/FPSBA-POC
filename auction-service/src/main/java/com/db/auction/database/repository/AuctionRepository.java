package com.db.auction.database.repository;

import com.db.auction.assets.AuctionStatus;
import com.db.auction.model.entity.Auction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AuctionRepository extends BaseRepository<Auction, Long> {

    List<Auction> findBySellerId(String sellerId);

    Optional<Auction> findByIdAndStatus(long id, AuctionStatus status);

    Optional<Auction> findByIdAndSellerId(long id, String sellerId);
}
