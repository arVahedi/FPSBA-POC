# First-Price Sealed-Bid Auction - Proof of Concept

## Requirements:

 - JDK 17
 - Maven

## Microservices:

The project is consisted of 3 different microservices as below: 

### user-service: 

**swagger**: [https://127.0.0.1:8443/doc/api/swagger-ui/index.html](https://127.0.0.1:8443/doc/api/swagger-ui/index.html)

Responsible for user (buyer and seller) management, all identity data of users is stored in this service (regarding GDPR)
and also it includes an endpoint for authenticating users and providing a JWT token.

There are different endpoints according to CRUD concept to managing entities, but the following endpoints are most important:

1. Creating User: Any user (regardless buyer or seller) should registers himself by this endpoint first and fill out the basic required data. After that this user can acts as a buyer
2. Create Seller: If any user would like to active his own seller panel, he/she needs to fill out required seller data by this endpoint, such as address, postal code and IBAN. **Please keep in mind since the IBAN is highly sensitive data, we encrypt value before saving in our database and then decrypt it everytime we need it.**
3. Authentication: The authentication endpoint is responsible to provide a JWT token wich contains user uid as a reference to current user in other micro-services.

The database is initialized by Flyway as well as inserting some test data. So by default the is 3 users (one seller and two buyer) with these username and password:

- seller_1@db.com : 123456
- buyer_1@db.com : 123456
- buyer_2@db.com : 123456

### product-service:

**swagger**: [https://127.0.0.1:8444/doc/api/swagger-ui/index.html](https://127.0.0.1:8444/doc/api/swagger-ui/index.html)

This microservice is responsible for managing all products registered by sellers.
Seller after creating its user and **also activating his own seller functionality** is able to create his own products here.

### auction-service:

**swagger**: [https://127.0.0.1:8445/doc/api/swagger-ui/index.html](https://127.0.0.1:8445/doc/api/swagger-ui/index.html)

There are required endpoints for handling an auction such as creating auctions, stop it, check results and also placing bids.
A seller after creating his own product by Product-Service needs to create an auction here (and indicates proper minimum acceptable price). Same previous microservices 
there are different endpoints regarding to CRUD concept but these below list are more important:

- Creating auction (accessible by seller)
- get list of all active auctions (This one can be accessed by buyer and seller)
- get result of auction: return summary situation of an auction without ending it (accessible only by the owner seller)
- placed bid
- stop auction: same as `get result` endpoint plus this one will stop an active auction


## Tests:

The code coverage of current tests is not 100% due to time limitation. I just tried to writing tests for main 3 functionalities based on task definition.

## What else:

However, event-driven communication between services here makes sense here in some scenarios, but I didn't go for it because basically that architecture needs a message queue in between and 
one of challenge conditions was reduce dependencies to outside world as much as possible according to security limitation of bank to run docker on developer's computers. 
