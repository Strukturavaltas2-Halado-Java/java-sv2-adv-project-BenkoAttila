CREATE TABLE erptransfers
(
   id  BIGINT AUTO_INCREMENT NOT NULL,
   started_at   DATETIME DEFAULT 0,
   finished_at  DATETIME DEFAULT 0,
   PRIMARY KEY (id)
);