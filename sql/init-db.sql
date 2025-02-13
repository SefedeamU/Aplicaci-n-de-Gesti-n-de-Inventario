CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10, 2) NOT NULL,
                          stock INT NOT NULL,
                          category VARCHAR(255) NOT NULL
);

CREATE TABLE customers (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) UNIQUE NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           role VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        product_ids BIGINT[] NOT NULL,
                        total DECIMAL(10, 2) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        date TIMESTAMP NOT NULL,
                        supplier VARCHAR(255) NOT NULL
);