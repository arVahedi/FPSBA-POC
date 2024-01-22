create sequence hibernate_sequence start with 100 increment by 1;

create table auction
(
    id                 bigint not null auto_increment,
    insert_date        timestamp,
    last_modified_date timestamp,
    product_id         bigint,
    minimum_price      int,
    status             smallint,
    seller_id          varchar(255),
    deleted            boolean,
    primary key (id)
);

create table bid
(
    id                 bigint not null auto_increment,
    insert_date        timestamp,
    last_modified_date timestamp,
    user_uid           varchar(255),
    auction_id         bigint,
    price              int,
    deleted            boolean,
    primary key (id)
)
