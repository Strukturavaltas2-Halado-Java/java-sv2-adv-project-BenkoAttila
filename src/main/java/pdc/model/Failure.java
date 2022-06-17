package pdc.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="failures")
public class Failure {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "firma_id", referencedColumnName = "firma_id"),
            @JoinColumn(name = "prodstufe_id", referencedColumnName = "prodstufe_id"),
            @JoinColumn(name = "pa_nr_id", referencedColumnName = "pa_nr_id")})
    private Prodauftrag prodauftrag;
    @Column(name="buendel_bc")
    private String buendelBc;
    @Column(name="ts_erfassung")
    private LocalDateTime tsErfassung;
    @ManyToOne
    @JoinColumn(name="personal_qc")
    private Personal personalQc;
    @ManyToOne
    @JoinColumn(name="personal_qc2")
    private Personal personalQc2;
    private Boolean pruefung2;
    @Column(name="abfall_id")
    private String abfallId;
    @ManyToOne
    @JoinColumn(name="personal_id")
    private Personal personal;
    @ManyToOne
    @JoinColumn(name="schichtplangruppe_id")
    private Schichtplangruppe schichtplangruppeId;
    @Column(name="menge_abfall")
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
}
