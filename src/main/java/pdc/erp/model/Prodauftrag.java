package pdc.erp.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
public class Prodauftrag {
    private int firmaId;
    private int bereichId;
    private int prodstufeId;
    private int paNrId;

    private double menge;
    private double fertigMge;
    private int liefNrId;
    private LocalDate termin;
    private boolean aktiv;
    private String kennzPartiewechsel;


    private Prodauftragartikel prodauftragartikel;

    private List<Prodauftrageint> prodauftrageintList = new ArrayList<>();

    private Prodauftragsonderfelder prodauftragsonderfelder;


    public void addProdaufragEint(Prodauftrageint pae) {
        pae.setFirmaId(firmaId);
        pae.setBereichId(bereichId);
        pae.setProdstufeId(prodstufeId);
        pae.setPaNrId(paNrId);
        prodauftrageintList.add(pae);
    }

    public void setProdauftragartikel(Prodauftragartikel prodauftragartikel) {
        prodauftragartikel.setFirmaId(firmaId);
        prodauftragartikel.setBereichId(bereichId);
        prodauftragartikel.setProdstufeId(prodstufeId);
        prodauftragartikel.setPaNrId(paNrId);
        this.prodauftragartikel = prodauftragartikel;
    }

    public void setProdauftragsonderfelder(Prodauftragsonderfelder prodauftragsonderfelder) {
        prodauftragsonderfelder.setFirmaId(firmaId);
        prodauftragsonderfelder.setBereichId(bereichId);
        prodauftragsonderfelder.setProdstufeId(prodstufeId);
        prodauftragsonderfelder.setPaNrId(paNrId);
        this.prodauftragsonderfelder = prodauftragsonderfelder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodauftrag that = (Prodauftrag) o;
        return firmaId == that.firmaId && bereichId == that.bereichId && prodstufeId == that.prodstufeId && paNrId == that.paNrId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmaId, bereichId, prodstufeId, paNrId);
    }
}
