create table users
(
    id       bigserial
        constraint users_pk
            primary key,
    name     varchar(255) not null,
    email    varchar(255) not null,
    password varchar(255) not null
);

alter table users
    owner to rayhan;

create table addresses
(
    id      bigserial
        constraint addresses_pk
            primary key,
    street  varchar(255) not null,
    city    varchar(255) not null,
    zip     varchar(255) not null,
    user_id integer
        constraint addresses_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table addresses
    owner to rayhan;

