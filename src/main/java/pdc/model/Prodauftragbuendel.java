package pdc.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="erpprodauftragbuendels")
public class Prodauftragbuendel {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   @ManyToOne
   @JoinColumn(name = "prodauftrag_id")
   private Prodauftrag prodauftrag;
   @Column(name="buendel_id")
   private int buendelId;
   @Column(name="karton_nr_id")
   private int kartonNrId;
   @Column(name="stueck_nr")
   private int stueckNr;
   @Column(name="buendelgruppe_id")
   private String buendelgruppeId;
   @Column(name="stueck_teilung")
   private int stueckTeilung;
   @Column(name="stapel_id")
   private int stapelId;
}
