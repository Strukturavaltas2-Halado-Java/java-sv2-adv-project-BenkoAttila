CREATE TABLE erpschichtplangruppen
(
   schichtplangruppe_id  INT NOT NULL,
   firma_id              INT NOT NULL,
   schichtplangruppe_bez VARCHAR(200),
   aktiv                 BOOLEAN,
   PRIMARY KEY (schichtplangruppe_id)
);