package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "erplagerbestdetails", indexes = @Index(name= "by_stueck_nr", columnList = "stueck_nr,stueck_teilung"))
public class Lagerbestdetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="prodauftrag_id")
    private Prodauftrag prodauftrag;
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
