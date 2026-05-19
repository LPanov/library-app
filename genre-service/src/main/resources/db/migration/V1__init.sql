CREATE TABLE genres (
                        id BINARY(16) PRIMARY KEY,
                        code VARCHAR(255) NOT NULL UNIQUE,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(255),
                        display_order INTEGER NOT NULL DEFAULT 0,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        parent_id BINARY(16),
                        created_at TIMESTAMP NULL,
                        updated_at TIMESTAMP NULL,

                        CONSTRAINT fk_genres_parent FOREIGN KEY (parent_id) REFERENCES genres (id) ON DELETE SET NULL
);