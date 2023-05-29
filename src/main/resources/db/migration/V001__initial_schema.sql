CREATE TABLE IF NOT EXISTS cards
(
    id              SERIAL,
    product_id      VARCHAR(6)     NOT NULL,
    number          VARCHAR(16)    NOT NULL,
    holder_name     VARCHAR(255)   NULL,
    expiration_date VARCHAR(7)     NOT NULL,
    is_active       BOOLEAN        DEFAULT false,
    is_blocked      BOOLEAN        DEFAULT false,
    balance         DECIMAL(10, 2) DEFAULT 0.00,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE ONLY cards
    ADD CONSTRAINT idx_card_number UNIQUE (number);

ALTER TABLE cards
    OWNER TO ${flyway:user};

CREATE SEQUENCE IF NOT EXISTS cards_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE cards_seq
    OWNER TO ${flyway:user};

ALTER SEQUENCE cards_seq OWNED BY cards.id;

ALTER TABLE ONLY cards
    ALTER COLUMN id SET DEFAULT nextval('cards_seq'::regclass);

SELECT pg_catalog.setval('cards_seq', 1, false);

ALTER TABLE ONLY cards
    ADD CONSTRAINT cards_pkey PRIMARY KEY (id);

CREATE TABLE IF NOT EXISTS transactions
(
    id               SERIAL,
    card_id          INT            NOT NULL,
    price            DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_cancelled     BOOLEAN        NOT NULL DEFAULT false,
    currency         VARCHAR(3)     NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE transactions
    OWNER TO ${flyway:user};

CREATE SEQUENCE IF NOT EXISTS transactions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE transactions_seq
    OWNER TO ${flyway:user};

ALTER SEQUENCE transactions_seq OWNED BY transactions.id;

ALTER TABLE ONLY transactions
    ALTER COLUMN id SET DEFAULT nextval('transactions_seq'::regclass);

SELECT pg_catalog.setval('transactions_seq', 1, false);

ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);

ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_fk02 FOREIGN KEY (card_id) REFERENCES cards (id);

