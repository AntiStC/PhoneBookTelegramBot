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
    user_id bigint unique primary key
);