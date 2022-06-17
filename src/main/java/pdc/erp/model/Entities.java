package pdc.erp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Entities {
    PRODAUFTRAG("prodauftrag", "pa"),
    PRODAUFTRAGARTIKEL("prodauftragartikel", "paartikel"),
    PRODAUFTRAGEINT("prodauftrageint", "paeint"),
    PRODAUFTRAGSONDERFELDER("prodauftragsonderfelder", "pasf_"),
    ARTIKELSTAMM("artikelstamm", "artikelstamm"),
    ARTIKELGROESSE("artikelgroesse", "artikelgroesse"),
    PRODUKTGRUPPE("produktgruppe", "produktgruppe"),
    ARTIKELSTAMM_ZUSATZ("artikelstamm_zusatz", "artz_");

    private final String table;
    private final String abbreviation;
}
