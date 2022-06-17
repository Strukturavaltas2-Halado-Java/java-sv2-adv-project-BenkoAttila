package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "erpprodauftragen")
public class Prodauftrag {
    @EmbeddedId
    private ProdauftragId id;
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

    public Prodauftrag(ProdauftragId id) {
        this.id = id;
    }
}
