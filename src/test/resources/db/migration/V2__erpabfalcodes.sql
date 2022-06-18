CREATE TABLE erpabfallcodes
(
  id                 BIGINT NOT NULL auto_increment,
  abfall_id          VARCHAR(255) NOT NULL,
  abfall_text        VARCHAR(255),
  fehler_gruppe_id   VARCHAR(255),
  firma_id           INTEGER NOT NULL,
  prodstufe_id       INTEGER NOT NULL,
  aktiv              BOOLEAN,
  PRIMARY KEY (id)
)
engine = InnoDB;
create index erppk on erpabfallcodes (firma_id, prodstufe_id, abfall_id);