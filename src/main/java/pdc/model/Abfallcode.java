package pdc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "erpabfallcodes", indexes = @Index(name= "erppk", columnList = "firma_id,prodstufe_id,abfall_id"))

@Data
@NoArgsConstructor
public class Abfallcode {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(name = "firma_id", nullable = false)
    private Integer firmaId;
    @Column(name = "prodstufe_id", nullable = false)
    private Integer prodstufeId;
    @Column(name = "abfall_id", nullable = false)
    private String abfallId;
    private boolean aktiv = true;
    @Column(name = "fehler_gruppe_id")
    private String fehlerGruppeId;
    @Column(name = "abfall_text")
    private String abfallText;

    public Abfallcode(Integer firmaId, Integer prodstufeId, String abfallId) {
        this.firmaId = firmaId;
        this.prodstufeId = prodstufeId;
        this.abfallId = abfallId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abfallcode that = (Abfallcode) o;
        return Objects.equals(firmaId, that.firmaId) && Objects.equals(prodstufeId, that.prodstufeId) && Objects.equals(abfallId, that.abfallId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmaId, prodstufeId, abfallId);
    }
}
