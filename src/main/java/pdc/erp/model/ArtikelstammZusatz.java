package pdc.erp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtikelstammZusatz {

    private int firmaId;
    private int bereichId;
    private String artikelKzId;
    private String artikelId;

    private Double gb1;
    private Double gb2;
    private Double gb3;
    private Integer schichtplangruppeId;
}
