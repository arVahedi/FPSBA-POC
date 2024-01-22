package com.db.auction.service;

import com.db.auction.assets.AuctionStatus;
import com.db.auction.database.repository.AuctionRepository;
import com.db.auction.exception.AuctionInactiveException;
import com.db.auction.exception.AuctionNotExistException;
import com.db.auction.model.dto.AuctionResult;
import com.db.auction.model.dto.crud.AuctionDto;
import com.db.auction.model.entity.Auction;
import com.db.auction.model.mapper.AuctionMapper;
import com.db.lib.utility.identity.IdentityUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionService extends BaseService implements DefaultCrudService<Long, Auction, AuctionDto> {

    @Getter
    private final AuctionRepository repository;
    @Getter
    private final AuctionMapper mapper;
    private BidService bidService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Auction save(AuctionDto auctionDto) {
        auctionDto.setSellerId(IdentityUtility.getUsername().orElseThrow());
        return DefaultCrudService.super.save(auctionDto);
    }

    public List<Auction> retrieveAllAuctionsOfSeller(String sellerUid) {
        return this.repository.findBySellerId(sellerUid);
    }

    public Optional<Auction> getActiveAuctionById(long auctionId) {
        return this.repository.findByIdAndStatus(auctionId, AuctionStatus.ACTIVE);
    }

    @Transactional(rollbackFor = Exception.class)
    public AuctionResult stopAuction(long auctionId) {
        AuctionResult auctionResult = getResult(auctionId);
        Auction auction = auctionResult.getAuction();
        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new AuctionInactiveException("Auction is already inactive");
        }
        auction.setStatus(AuctionStatus.SUSPENDED);
        this.repository.save(auction);

        return auctionResult;
    }

    public AuctionResult getResult(long auctionId) {
        Auction auction = this.repository.findByIdAndSellerId(auctionId, IdentityUtility.getUsername().get()).orElseThrow(() -> new AuctionNotExistException("The target auction doesn't exist"));
        return AuctionResult.builder()
                .auction(auction)
                .bestBid(this.bidService.getBestBid(auctionId).orElse(null))
                .allBids(this.bidService.getAllBidsForAuction(auctionId))
                .build();
    }

    @Autowired
    @Lazy
    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }
}
