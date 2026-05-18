CREATE TABLE genres (
                        id VARCHAR(36) PRIMARY KEY,
                        code VARCHAR(255) NOT NULL UNIQUE,
                        name VARCHAR(255) NOT NULL,
                        description VARCHAR(255),
                        display_order INTEGER NOT NULL DEFAULT 0,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        parent_id UUID,
                        created_at TIMESTAMP WITHOUT TIME ZONE,
                        updated_at TIMESTAMP WITHOUT TIME ZONE,

                        CONSTRAINT fk_genres_parent FOREIGN KEY (parent_id) REFERENCES genres (id) ON DELETE SET NULL
);