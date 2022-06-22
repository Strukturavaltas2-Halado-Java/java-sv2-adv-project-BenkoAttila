CREATE TABLE erpprodauftragen
(
    id                     BIGINT NOT NULL auto_increment,
    artikel_id             VARCHAR(255),
    farbe_id               VARCHAR(255),
    fertigungszustand_id   VARCHAR(255),
    firma_id               INTEGER NOT NULL,
    groesse_id             VARCHAR(255),
    kennz_partiewechsel    VARCHAR(255),
    menge                  DOUBLE precision NOT NULL,
    pa_nr_id               INTEGER NOT NULL,
    prodstufe_id           INTEGER NOT NULL,
    variante_id            VARCHAR(255),
    aktiv                  BOOLEAN,
    PRIMARY KEY (id)
)
    engine = InnoDB;
create index erppk on erpprodauftragen (firma_id, prodstufe_id, pa_nr_id);