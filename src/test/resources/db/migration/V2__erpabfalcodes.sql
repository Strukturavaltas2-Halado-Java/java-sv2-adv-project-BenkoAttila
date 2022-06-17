CREATE TABLE erpabfallcodes
(
   firma_id          INT           DEFAULT 0 NOT NULL,
   prodstufe_id      INT           DEFAULT 0 NOT NULL,
   abfall_id         VARCHAR(6)    DEFAULT '' NOT NULL,
   aktiv             BOOLEAN,
   fehler_gruppe_id  VARCHAR(6),
   abfall_text       VARCHAR(80),
   PRIMARY KEY (firma_id, prodstufe_id, abfall_id)
)