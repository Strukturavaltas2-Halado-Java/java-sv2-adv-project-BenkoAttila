package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "erplagerbestdetails")
public class Lagerbestdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firma_id")
    private int firmaId;
    @Column(name = "prodstufe_id")
    private int prodstufeId;
    @Column(name = "pa_nr_id")
    private int paNrId;
    @Column(name = "stueck_nr")
    private int stueckNr;
    @Column(name = "stueck_teilung")
    private int stueckTeilung;
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

    public Lagerbestdetail(int stueckNr, int stueckTeilung) {
        this.stueckNr = stueckNr;
        this.stueckTeilung = stueckTeilung;
    }
}
