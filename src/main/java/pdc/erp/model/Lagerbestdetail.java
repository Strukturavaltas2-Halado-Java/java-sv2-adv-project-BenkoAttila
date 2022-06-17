package pdc.erp.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Lagerbestdetail {
    private int stueckNr;
    private int stueckTeilung;
    private int prodstufeId;
    private int paNrId;
    private Lagerbestglobal lagerbestglobal;

}
