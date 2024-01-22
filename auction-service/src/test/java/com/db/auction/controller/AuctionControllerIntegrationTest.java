package com.db.auction.controller;

import com.db.auction.assets.AuctionStatus;
import com.db.auction.database.repository.AuctionRepository;
import com.db.auction.database.repository.BidRepository;
import com.db.auction.model.dto.crud.AuctionDto;
import com.db.auction.model.entity.Auction;
import com.db.auction.model.entity.Bid;
import com.db.lib.assets.AuthorityType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class AuctionControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private BidRepository bidRepository;
    private MockMvc mockMvc;

    private static final int AUCTION_MIN_PRICE = 100;
    private static final String SELLER_MOCK_UID = "dddb44e5-e0af-4bf6-8841-5a69927ea44f";


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = AuthorityType.StringFormat.SELLER_AUTHORITY)
    void givenAuctionDto_whenMockMVC_thenVerifyCreatedAuctionItem() throws Exception {
        this.mockMvc.perform(post(BaseRestController.API_PATH_PREFIX_V1 + "/auctions")
                        .content(this.objectMapper.writeValueAsString(getSampleAuctionDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = SELLER_MOCK_UID, authorities = AuthorityType.StringFormat.SELLER_AUTHORITY)
    void givenStopAuction_whenMockMVC_thenResultAuctionWithBestBid() throws Exception {
        Auction auction = createSampleAuction();
        Bid bestBid = createMultipleMockBid(auction);

        this.mockMvc.perform(post(BaseRestController.API_PATH_PREFIX_V1 + "/auctions/" + auction.getId() + "/stop"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.bestBid.id").value(bestBid.getId()))
                .andReturn();
    }

    Auction createSampleAuction() {
        Auction auction = new Auction();
        auction.setStatus(AuctionStatus.ACTIVE);
        auction.setProductId(1);
        auction.setMinimumPrice(AUCTION_MIN_PRICE);
        auction.setSellerId(SELLER_MOCK_UID);

        auction = this.auctionRepository.save(auction);
        this.auctionRepository.flush();
        return auction;
    }

    Bid createMultipleMockBid(Auction auction) {
        Bid bestBid = null;
        for (int counter = 0; counter < 5; counter++) {
            Bid bid = new Bid();
            bid.setAuction(auction);
            bid.setPrice(AUCTION_MIN_PRICE + counter);
            bid.setUserUid(UUID.randomUUID().toString());

            bid = this.bidRepository.save(bid);
            bestBid = bid;
        }

        return bestBid;
    }

    private AuctionDto getSampleAuctionDto() {
        AuctionDto auctionDto = new AuctionDto();
        auctionDto.setProductId(1);
        auctionDto.setMinimumPrice(100);
        return auctionDto;
    }
}