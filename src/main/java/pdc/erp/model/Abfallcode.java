package pdc.erp.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Abfallcode {
    private int firmaId;
    private Integer prodstufeId;
    private String abfallId;
    private String fehlerGruppeId;
    private String abfallText;
}
