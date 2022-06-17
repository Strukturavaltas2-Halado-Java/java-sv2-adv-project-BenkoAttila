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
   @JoinColumns({
           @JoinColumn(name = "firma_id", referencedColumnName = "firma_id"),
           @JoinColumn(name = "prodstufe_id", referencedColumnName = "prodstufe_id"),
           @JoinColumn(name = "pa_nr_id", referencedColumnName = "pa_nr_id")})
   private Prodauftrag prodauftrag;
   private int buendelId;
   private int kartonNrId;
   private int stueckNr;
   private String buendelgruppeId;
   private int stueckTeilung;
}
