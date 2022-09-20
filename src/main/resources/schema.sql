create table if not exists person
(
    id          bigint unique primary key,
    first_name  varchar(20) not null,
    middle_name varchar(20) not null,
    last_name   varchar(20) not null,
    department  varchar     not null,
    phone       int         not null,
    mobil_phone bigint      not null
);

create table if not exists user_bot
(
    id              bigint unique primary key,
    user_id         bigint  not null,
    user_first_name varchar not null,
    user_last_name  varchar
);