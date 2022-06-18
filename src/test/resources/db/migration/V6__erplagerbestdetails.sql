CREATE TABLE erplagerbestdetails
(
   id                    BIGINT AUTO_INCREMENT NOT NULL,
   prodauftrag_id        BIGINT,
   stueck_nr             INT           DEFAULT 0 NOT NULL,
   stueck_teilung        INT           DEFAULT 0 NOT NULL,
   artikel_id            VARCHAR(30),
   groesse_id            VARCHAR(16),
   farbe_id              VARCHAR(16),
   variante_id           VARCHAR(16),
   fertigungszustand_id  VARCHAR(4),
   PRIMARY KEY (id)
);

CREATE INDEX erplagerbestdetails1
  ON erplagerbestdetails (stueck_nr, stueck_teilung);
alter table erplagerbestdetails add constraint FKhnhv8pabncnbec201m19fpa8n foreign key (prodauftrag_id) references erpprodauftragen (id);