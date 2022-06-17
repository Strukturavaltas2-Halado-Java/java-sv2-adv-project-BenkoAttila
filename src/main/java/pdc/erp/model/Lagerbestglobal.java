package pdc.erp.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Lagerbestglobal {
    private int firmaId;
    private int bereichId;
    private String artikelKzId;
    private String artikelId;
    private String groesseId;
    private String farbeId;
    private String varianteId;
    private String fertigungszustandId;
    private String warenzustandId;
    private int lagerortId;
    private int meId;
    private double mengeFrei;
    private double mengePa;
    List<Lagerbestdetail> details = new ArrayList<>();

    public void addDetail(Lagerbestdetail lagerbestdetail) {
        details.add(lagerbestdetail);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lagerbestglobal that = (Lagerbestglobal) o;
        return firmaId == that.firmaId && bereichId == that.bereichId && lagerortId == that.lagerortId && Objects.equals(artikelKzId, that.artikelKzId) && Objects.equals(artikelId, that.artikelId) && Objects.equals(groesseId, that.groesseId) && Objects.equals(farbeId, that.farbeId) && Objects.equals(fertigungszustandId, that.fertigungszustandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmaId, bereichId, artikelKzId, artikelId, groesseId, farbeId, fertigungszustandId, lagerortId);
    }
}
