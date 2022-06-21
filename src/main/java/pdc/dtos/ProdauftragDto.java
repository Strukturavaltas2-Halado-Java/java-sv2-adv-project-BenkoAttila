package pdc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdauftragDto {
    private int firmaId;
    private int prodstufeId;
    private int paNrId;
    private boolean aktiv;
    private double menge;
    private String artikelId;
    private String groesseId;
    private String farbeId;
    private String varianteId;
    private String fertigungszustandId;
}
