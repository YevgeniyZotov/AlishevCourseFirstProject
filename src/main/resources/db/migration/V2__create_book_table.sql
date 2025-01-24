CREATE TABLE book
(
    id        BIGSERIAL PRIMARY KEY,
    title     VARCHAR(200) NOT NULL,
    author    VARCHAR(100) NOT NULL,
    year      INT          NOT NULL,
    person_id INT,
    CONSTRAINT fk_person FOREIGN KEY (person_id) REFERENCES person (id)
);
