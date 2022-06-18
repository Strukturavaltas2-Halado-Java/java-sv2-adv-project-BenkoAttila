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
   private int buendelId;
   private int kartonNrId;
   private int stueckNr;
   private String buendelgruppeId;
   private int stueckTeilung;
}
