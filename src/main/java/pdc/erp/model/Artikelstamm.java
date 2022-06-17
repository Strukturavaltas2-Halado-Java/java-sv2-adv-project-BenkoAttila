package pdc.erp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Artikelstamm {
    private int firmaId;
    private int bereichId;
    private String artikelKzId;
    private String artikelId;
    private int prodGrpId;
    private int produktgruppeId;

    private String artBez1;
    private String artBez2;

    private ArtikelstammZusatz artikelstammZusatz;

    Produktgruppe produktgruppe;

    public void setProduktgruppe(Produktgruppe produktgruppe) {
        produktgruppe.setFirmaId(firmaId);
        produktgruppe.setBereichId(bereichId);
        produktgruppe.setProdGrpId(prodGrpId);
        this.produktgruppe = produktgruppe;
    }

    public void setArtikelstammZusatz(ArtikelstammZusatz artikelstammZusatz) {
        artikelstammZusatz.setFirmaId(firmaId);
        artikelstammZusatz.setBereichId(bereichId);
        artikelstammZusatz.setArtikelKzId(artikelKzId);
        artikelstammZusatz.setArtikelId(artikelId);
        this.artikelstammZusatz = artikelstammZusatz;
    }
}
