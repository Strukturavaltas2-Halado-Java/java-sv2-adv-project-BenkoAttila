package pdc.erp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter

public class Prodauftragartikel {
    private int firmaId;
    private int bereichId;
    private int prodstufeId;
    private int paNrId;

    private String artikelKzId;

    private String artikelId;
    private String groesseId;
    private String farbeId;
    private String varianteId;
    private String fertigungszustandId;

    private Artikelgroesse artikelgroesse;
    private Artikelstamm artikelstamm;

}
