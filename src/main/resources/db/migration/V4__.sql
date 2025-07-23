CREATE TABLE addresses
(
    id      CHAR(36) NOT NULL,
    user_id CHAR(36),
    city    VARCHAR(255),
    street  VARCHAR(255),
    zip     VARCHAR(255),
    CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

CREATE TABLE categories
(
    id   CHAR(36) NOT NULL,
    name VARCHAR(255),
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);

CREATE TABLE orders
(
    net_price DOUBLE PRECISION,
    id        CHAR(36) NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

CREATE TABLE products
(
    price       DOUBLE PRECISION,
    category_id CHAR(36),
    id          CHAR(36)     NOT NULL,
    description VARCHAR(255),
    name        VARCHAR(255) NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

CREATE TABLE profiles
(
    date_of_birth  date,
    loyalty_points INTEGER,
    id             CHAR(36) NOT NULL,
    user_id        CHAR(36),
    bio            VARCHAR(255),
    phone_number   VARCHAR(255),
    CONSTRAINT profiles_pkey PRIMARY KEY (id)
);

CREATE TABLE tags
(
    id          CHAR(36) NOT NULL,
    description VARCHAR(255),
    name        VARCHAR(255),
    CONSTRAINT tags_pkey PRIMARY KEY (id)
);

CREATE TABLE user_tags
(
    tag_id  CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    CONSTRAINT user_tags_pkey PRIMARY KEY (tag_id, user_id)
);

CREATE TABLE users
(
    id       CHAR(36) NOT NULL,
    email    VARCHAR(255),
    name     VARCHAR(255),
    password VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE wishlists
(
    id         CHAR(36) NOT NULL,
    product_id CHAR(36) NOT NULL,
    user_id    CHAR(36) NOT NULL,
    CONSTRAINT wishlists_pkey PRIMARY KEY (id)
);

ALTER TABLE profiles
    ADD CONSTRAINT profiles_user_id_key UNIQUE (user_id);

ALTER TABLE addresses
    ADD CONSTRAINT fk1fa36y2oqhao3wgg2rw1pi459 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE wishlists
    ADD CONSTRAINT fk330pyw2el06fn5g28ypyljt16 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE profiles
    ADD CONSTRAINT fk410q61iev7klncmpqfuo85ivh FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE user_tags
    ADD CONSTRAINT fkdylhtw3qjb2nj40xp50b0p495 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

ALTER TABLE user_tags
    ADD CONSTRAINT fkioatd2q4dvvsb5k6al6ge8au4 FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE NO ACTION;

ALTER TABLE wishlists
    ADD CONSTRAINT fkl7ao98u2bm8nijc1rv4jobcrx FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE;

ALTER TABLE products
    ADD CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE NO ACTION;