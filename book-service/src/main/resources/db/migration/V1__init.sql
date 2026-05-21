CREATE TABLE books
(
    id                 UUID          NOT NULL,
    isbn               VARCHAR(20)   NOT NULL,
    title              VARCHAR(255)  NOT NULL,
    author             VARCHAR(255)  NOT NULL,
    genre_id           UUID          NOT NULL,
    publisher          VARCHAR(255),
    language           VARCHAR(20),
    publication_date   DATE,
    pages              INTEGER,
    description        TEXT,
    copies             INTEGER          NOT NULL,
    available_copies   INTEGER          NOT NULL,
    price              NUMERIC(18, 2),
    cover_image_url    VARCHAR(500),
    active             BOOLEAN       DEFAULT TRUE NOT NULL,
    created_at         TIMESTAMP WITHOUT TIME ZONE,
    updated_at         TIMESTAMP WITHOUT TIME ZONE,

    CONSTRAINT pk_books PRIMARY KEY (id),
    CONSTRAINT uq_books_isbn UNIQUE (isbn)
);