package com.db.auction.controller;

import com.db.auction.assets.AuctionStatus;
import com.db.auction.database.repository.AuctionRepository;
import com.db.auction.model.dto.crud.BidDto;
import com.db.auction.model.entity.Auction;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class BidControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuctionRepository auctionRepository;
    private MockMvc mockMvc;

    private static final int AUCTION_MIN_PRICE = 100;
    private static final String SELLER_MOCK_UID = "dddb44e5-e0af-4bf6-8841-5a69927ea44f";

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @WithMockUser
    @Transactional
    void givenBidDto_whenMockMVC_thenVerifyCreatedBid() throws Exception {
        Auction auction = createSampleAuction();

        BidDto bidDto = new BidDto();
        bidDto.setAuctionId(auction.getId());
        bidDto.setPrice(AUCTION_MIN_PRICE + 100);

        this.mockMvc.perform(post(BaseRestController.API_PATH_PREFIX_V1 + "/bid/placed")
                        .content(this.objectMapper.writeValueAsString(bidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser
    @Transactional
    void givenBidLessThanMinimumPrice_whenMockMVC_thenShouldBeRejected() throws Exception {
        Auction auction = createSampleAuction();

        BidDto bidDto = new BidDto();
        bidDto.setAuctionId(auction.getId());
        bidDto.setPrice(AUCTION_MIN_PRICE - 1);

        this.mockMvc.perform(post(BaseRestController.API_PATH_PREFIX_V1 + "/bid/placed")
                        .content(this.objectMapper.writeValueAsString(bidDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser
    @Transactional
    void givenMultipleBid_whenMockMVC_thenShouldBeAccepted() throws Exception {
        Auction auction = createSampleAuction();

        BidDto bidDto = new BidDto();
        bidDto.setAuctionId(auction.getId());
        for (int counter = 0; counter < 3; counter++) {
            bidDto.setPrice(AUCTION_MIN_PRICE + counter);

            this.mockMvc.perform(post(BaseRestController.API_PATH_PREFIX_V1 + "/bid/placed")
                            .content(this.objectMapper.writeValueAsString(bidDto))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
        }
    }

    Auction createSampleAuction() {
        Auction auction = new Auction();
        auction.setStatus(AuctionStatus.ACTIVE);
        auction.setProductId(1);
        auction.setMinimumPrice(AUCTION_MIN_PRICE);
        auction.setSellerId(SELLER_MOCK_UID);

        return this.auctionRepository.save(auction);
    }

}
