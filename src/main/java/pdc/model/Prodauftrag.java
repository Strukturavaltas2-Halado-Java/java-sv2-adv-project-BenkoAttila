package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "erpprodauftragen", indexes = @Index(name= "erppk", columnList = "firma_id,prodstufe_id,pa_nr_id"))
public class Prodauftrag {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firma_id", nullable = false)
    private Integer firmaId;
    @Column(name = "prodstufe_id", nullable = false)
    private Integer prodstufeId;
    @Column(name = "pa_nr_id", nullable = false)
    private Integer paNrId;
    private boolean aktiv;
    private double menge;
    @Column(name = "artikel_id")
    private String artikelId;
    @Column(name = "groesse_id")
    private String groesseId;
    @Column(name = "farbe_id")
    private String farbeId;
    @Column(name = "variante_id")
    private String varianteId;
    @Column(name = "fertigungszustand_id")
    private String fertigungszustandId;
    @Column(name = "kennz_partiewechsel")
    private String kennzPartiewechsel;

    public Prodauftrag(Integer firmaId, Integer prodstufeId, Integer paNrId) {
        this.firmaId = firmaId;
        this.prodstufeId = prodstufeId;
        this.paNrId = paNrId;
    }


}
