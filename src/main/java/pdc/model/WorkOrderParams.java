package pdc.model;

import lombok.Getter;
import lombok.ToString;
import pdc.exceptions.InvalidBarcodeException;
import pdc.exceptions.StapeBuendelFormatException;

import javax.validation.constraints.Min;
import java.util.Optional;

@Getter
@ToString
public class WorkOrderParams {
    @Min(1)
    private int firmaId;
    private int prodstufeId;
    private int paNrId;

    private int stapelId;
    private int buendel1;
    private int buendel2;
    private int buendel3;
    private String stueckNrBc;
    private int stueckNr;
    private int stueckTeilung;

    public WorkOrderParams(int firmaId, int prodstufeId, int paNrId) {
        this.firmaId = firmaId;
        this.prodstufeId = prodstufeId;
        this.paNrId = paNrId;
    }

    public void setStapelBuendel(Optional<String> stapelId, Optional<String> buendel1, Optional<String> buendel2, Optional<String> buendel3) {
        try {
            if (stapelId.isPresent()) {
                this.stapelId = Integer.parseInt(stapelId.get());
            }
            if (buendel1.isPresent()) {
                this.buendel1 = Integer.parseInt(buendel1.get());
            }
            if (buendel2.isPresent()) {
                this.buendel2 = Integer.parseInt(buendel2.get());
            }
            if (buendel3.isPresent()) {
                this.buendel3 = Integer.parseInt(buendel3.get());
            }
        } catch (NumberFormatException nfe) {
            throw new StapeBuendelFormatException(stapelId, buendel1, buendel2, buendel3, nfe.getMessage());
        }
    }

    public ProdauftragenFilterType getFilterType() {
        if (stueckNr > 0) {
            return ProdauftragenFilterType.BY_STUECK_NR;
        }
        if (stapelId > 0) {
            return ProdauftragenFilterType.BY_BUENDEL;
        }
        return ProdauftragenFilterType.BY_NR;
    }

    public void setStueckNrBc(Optional<String> stueckNrBc) {
        if (stueckNrBc.isPresent()) {
            this.stueckNrBc = stueckNrBc.get().trim();
            try {
                decodeBarcode();
            } catch (IllegalArgumentException e){
                throw new InvalidBarcodeException(this.stueckNrBc, e.getMessage());
            }
        }
    }

    private void decodeBarcode() {
        String[] parts = stueckNrBc.split("/");
        switch (parts.length) {
            case 1:
                stueckNr = Integer.parseInt(parts[0]);
                break;
            case 2:
                stueckNr = Integer.parseInt(parts[0]);
                stueckTeilung = Integer.parseInt(parts[1]);
                break;
            default:
                throw new IllegalArgumentException(String.format("Invalid barcode (%s) !", stueckNrBc));
        }
    }
}