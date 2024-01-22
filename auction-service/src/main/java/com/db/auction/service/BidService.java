package com.db.auction.service;

import com.db.auction.database.repository.BidRepository;
import com.db.auction.exception.AuctionNotExistException;
import com.db.auction.exception.BidMinimumPriceViolationException;
import com.db.auction.model.dto.crud.BidDto;
import com.db.auction.model.entity.Auction;
import com.db.auction.model.entity.Bid;
import com.db.auction.model.mapper.BidMapper;
import com.db.lib.utility.identity.IdentityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidService extends BaseService implements DefaultCrudService<Long, Bid, BidDto> {

    @Getter
    private final BidRepository repository;
    @Getter
    private final BidMapper mapper;
    private final AuctionService auctionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bid save(BidDto bidDto) {
        Bid bid = this.mapper.toEntity(bidDto);

        String uid = IdentityUtility.getUsername().orElseThrow();
        bid.setUserUid(uid);

        Auction auction = this.auctionService.getActiveAuctionById(bidDto.getAuctionId()).orElseThrow(() -> new AuctionNotExistException("The target auction doesn't exist"));

        if (bid.getPrice() < auction.getMinimumPrice()) {
            throw new BidMinimumPriceViolationException(MessageFormat.format("The minimum acceptable price is {0}", auction.getMinimumPrice()));
        }

        deleteAllBidOfUserOnSpecialAuction(auction.getId(), uid);
        bid.setAuction(auction);

        return this.repository.save(bid);
    }

    public Optional<Bid> getBestBid(long auctionId) {
        Auction auction = this.auctionService.find(auctionId);
        return this.repository.findFirstByAuctionOrderByPriceDesc(auction);
    }

    public List<Bid> getAllBidsForAuction(long auctionId) {
        Auction auction = this.auctionService.find(auctionId);
        return this.repository.findAllByAuction(auction);
    }

    private void deleteAllBidOfUserOnSpecialAuction(Long auctionId, String userUid) {
        this.repository.deleteAllByAuctionAndUserUid(auctionId, userUid);
        this.repository.flush();
    }
}
