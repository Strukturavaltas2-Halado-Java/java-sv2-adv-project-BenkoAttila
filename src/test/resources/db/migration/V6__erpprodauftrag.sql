CREATE TABLE erpprodauftragen
(
   firma_id              INT             DEFAULT 0 NOT NULL,
   prodstufe_id          INT             DEFAULT 0 NOT NULL,
   pa_nr_id              INT             DEFAULT 0 NOT NULL,
   aktiv                 BOOLEAN,
   menge                 DECIMAL(17,2),
   artikel_id            VARCHAR(30),
   groesse_id            VARCHAR(16),
   farbe_id              VARCHAR(16),
   variante_id           VARCHAR(16),
   fertigungszustand_id  VARCHAR(4),
   kennz_partiewechsel   VARCHAR(15),
   PRIMARY KEY (firma_id, prodstufe_id, pa_nr_id)
)