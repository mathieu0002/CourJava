CREATE TABLE livre
(
    id               INTEGER,
    titre            VARCHAR(255),
    auteur           VARCHAR(255),
    nb_pages         INTEGER,
    editeur          VARCHAR(255),
    date_publication date,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    CONSTRAINT pk_livre PRIMARY KEY (id)
);