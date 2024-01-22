package com.db.auction.model.mapper;

import com.db.auction.configuration.GlobalMapperConfig;
import com.db.auction.model.dto.crud.BidDto;
import com.db.auction.model.entity.Bid;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface BidMapper extends BaseCrudMapper<Bid, BidDto> {

}
