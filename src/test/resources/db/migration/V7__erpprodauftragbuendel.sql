CREATE TABLE erpprodauftragbuendels
(
    id                 BIGINT NOT NULL auto_increment,
    buendel_id         INTEGER NOT NULL,
    buendelgruppe_id   VARCHAR(255),
    karton_nr_id       INTEGER NOT NULL,
    stueck_nr          INTEGER NOT NULL,
    stueck_teilung     INTEGER NOT NULL,
    prodauftrag_id     BIGINT,
    PRIMARY KEY (id)
)
    engine = InnoDB;
alter table erpprodauftragbuendels add constraint FK1nl1gvup107qrvgego6hk6ju2 foreign key (prodauftrag_id) references erpprodauftragen (id);