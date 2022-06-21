package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="failures")
@NoArgsConstructor
@Getter
@Setter
public class Failure {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="prodauftrag_id")
    private Prodauftrag prodauftrag;
    @Column(name="buendel_bc")
    private String buendelBc;
    @Column(name="ts_erfassung")
    private LocalDateTime tsErfassung;
    @ManyToOne
    @JoinColumn(name="personal_qc")
    private Personal personalQc;
    @ManyToOne()
    @JoinColumn(name="personal_qc2")
    private Personal personalQc2;
    private Boolean pruefung2;
    @ManyToOne
    @JoinColumn(name="abfall_id")
    private Abfallcode abfallcode;
    @ManyToOne
    @JoinColumn(name="personal_id")
    private Personal personal;
    @ManyToOne
    @JoinColumn(name="schichtplangruppe_id")
    private Schichtplangruppe schichtplangruppe;
    @Column(name="menfge_abfall")
    private Double mengeAbfall;
    @Column(name="stueck_nr")
    private Integer stueckNr;
    @Column(name="stueck_teilung")
    private Integer stueckTeilung;
    @Column(name="menge_gutz")
    private Double mengeGutz;
    @Column(name="zuschnitt_fertig")
    private Boolean zuschnittFertig;
    @Column(name="menge_gutp")
    private Double mengeGutp;
    @Column(name="pruefung_fertig")
    private Boolean pruefungFertig;

    public Failure(Prodauftrag prodauftrag) {
        this.prodauftrag = prodauftrag;
    }
}
