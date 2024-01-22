package com.db.auction.database.repository;

import com.db.auction.model.entity.Auction;
import com.db.auction.model.entity.Bid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface BidRepository extends BaseRepository<Bid, Long> {

    @Modifying
    @Query("update Bid b set b.deleted = true where b.auction.id = :auctionId and b.userUid = :userUid")
    void deleteAllByAuctionAndUserUid(long auctionId, String userUid);

    Optional<Bid> findFirstByAuctionOrderByPriceDesc(Auction auction);

    List<Bid> findAllByAuction(Auction auction);
}
