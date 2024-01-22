create sequence hibernate_sequence start with 100 increment by 1;

create table users
(
    id                 bigint not null auto_increment,
    insert_date        timestamp,
    last_modified_date timestamp,
    first_name         varchar(255),
    last_name          varchar(255),
    password           varchar(255),
    email              varchar(255),
    uid                varchar(255),
    status             smallint,
    deleted            boolean,
    primary key (id)
);

create table seller_info
(
    id                 bigint not null auto_increment,
    insert_date        timestamp,
    last_modified_date timestamp,
    address            varchar(255),
    postal_code        varchar(20),
    iban               varchar(255),
    user_id            bigint,
    primary key (id)
);

alter table seller_info
    add constraint FK1qx6hq1etrw5gu17srch9b1y4 foreign key (user_id) references users;

INSERT INTO users (id, email, password, insert_date, last_modified_date, uid, status, deleted)
values (default, 'seller_1@db.com',
        '$argon2id$v=19$m=4096,t=3,p=1$TRWRrR2qAo4NSeBZoyzaKQ$aEtA9KUayLLKaLD8SMKXlvQP0Pqwjd3S+1mQjJOuaPk',
        current_timestamp, NULL,
        '81df2259-f045-4e70-86be-3db5cbecd009', 1, false),
       (default, 'buyer_1@db.com',
        '$argon2id$v=19$m=4096,t=3,p=1$TRWRrR2qAo4NSeBZoyzaKQ$aEtA9KUayLLKaLD8SMKXlvQP0Pqwjd3S+1mQjJOuaPk',
        current_timestamp, NULL,
        'fcde717a-52e2-4e40-925c-199846897fb2', 1, false),
       (default, 'buyer_2@db.com',
        '$argon2id$v=19$m=4096,t=3,p=1$TRWRrR2qAo4NSeBZoyzaKQ$aEtA9KUayLLKaLD8SMKXlvQP0Pqwjd3S+1mQjJOuaPk',
        current_timestamp, NULL,
        '29b8be56-0de0-495c-a7bd-8dd902f26c8f', 1, false);

INSERT INTO seller_info (id, address, postal_code, iban, user_id, insert_date, last_modified_date)
VALUES (default, 'Wilhelmstraße 77-78 Berlin', '10117', 'z15wbxuJAwNAadkkmkmiwqtNWeJ3meZs6HAfhigsuH4=', 1, current_timestamp, NULL);