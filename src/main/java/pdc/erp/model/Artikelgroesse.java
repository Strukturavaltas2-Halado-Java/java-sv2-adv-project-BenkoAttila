package pdc.erp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Artikelgroesse {
    private int firmaId;
    private int bereichId;
    private String artikelKzId;
    private String artikelId;
    private String groesseId;

    private String groeKtext;
}
