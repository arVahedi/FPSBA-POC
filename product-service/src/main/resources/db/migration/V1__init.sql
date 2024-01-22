create sequence hibernate_sequence start with 100 increment by 1;

create table product
(
    id                 bigint not null auto_increment,
    insert_date        timestamp,
    last_modified_date timestamp,
    name               varchar(255),
    description        varchar(255),
    seller              varchar(255),
    primary key (id)
);
