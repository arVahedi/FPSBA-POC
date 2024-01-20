# First-Price Sealed-Bid Auction - Proof of Concept

## Requirements:

 - JDK 17
 - Maven

## Endpoints:

### auth-server:
- Register as a seller
- Register as a buyer
- Login

### auction-server:
- Add new product for auction (with minimum bid) [Seller]
- End the auction and see the winner and their bid [Seller]
- Submit a bid in an auction (any number of times) [Buyer]
- Get the list of existing auctions and details of product [Buyer]