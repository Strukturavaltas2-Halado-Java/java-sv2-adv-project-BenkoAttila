CREATE TABLE erppersonals
(
   personal_id           INT NOT NULL,
   firma_id              INT NOT NULL,
   pers_name             VARCHAR(200),
   datum_austritt        DATE,
   PRIMARY KEY (personal_id)
);
