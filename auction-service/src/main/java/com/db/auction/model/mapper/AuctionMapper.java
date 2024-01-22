package com.db.auction.model.mapper;

import com.db.auction.configuration.GlobalMapperConfig;
import com.db.auction.model.dto.crud.AuctionDto;
import com.db.auction.model.entity.Auction;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface AuctionMapper extends BaseCrudMapper<Auction, AuctionDto> {

}
