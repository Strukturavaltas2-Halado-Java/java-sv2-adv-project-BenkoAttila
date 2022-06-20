package pdc.erp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class Prodauftragbuendel {
   private int firmaId;
   private int bereichId;
   private int prodstufeId;
   private int paNrId;
   private int buendelId;
   private int kartonNrId;
   private int stueckNr;
   private String buendelgruppeId;
   private int stueckTeilung;
   private int stapelId;
}
