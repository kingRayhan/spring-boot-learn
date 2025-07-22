create table public.profiles
(
    id             bigserial
        constraint profiles_pk
            primary key
        constraint profiles_users_id_fk
            references public.users,
    bio            varchar(255),
    phone_number   varchar(255),
    date_of_birth  date,
    loyalty_points integer default 0
);

