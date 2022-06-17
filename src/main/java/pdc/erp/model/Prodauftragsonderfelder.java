package pdc.erp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Setter
public class Prodauftragsonderfelder {
    private int firmaId;
    private int bereichId;
    private int prodstufeId;
    private int paNrId;

    private Integer schichtplangruppeId;
    private LocalDate zustermin;
    private LocalDate naetermin;
    private String buendelInfo;
    private boolean apFertig;
    private boolean szabvany;
}
