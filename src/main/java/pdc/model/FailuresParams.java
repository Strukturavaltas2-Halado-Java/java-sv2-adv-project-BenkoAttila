package pdc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pdc.exceptions.IlegalParamException;

import javax.validation.constraints.Min;

import java.util.Optional;

import static pdc.model.FailuresQueryType.*;

@Getter
@Setter
@ToString
public class FailuresParams {
    private final static int DEFAULT_COUNT = 3;
    @Min(1)
    private int firmaId;
    private int prodstufeId;
    private int paNrId;
    private int id;
    private int hours = 12;
    private int personalId;
    private String buendelBc;
    private String abfallId;
    private boolean withStueckNr;
    private int count;

    private FailuresQueryType failuresQueryType = BY_PA_NR;

    public FailuresParams(Optional<Integer> optionalFirmaId, Optional<Integer> optionalProdstufeId, Optional<Integer> optionalPaNrId) {
        if (optionalFirmaId.isPresent()) {
            this.firmaId = optionalFirmaId.get();
            failuresQueryType = BY_FIRMA_ID;
        }
        if (optionalProdstufeId.isPresent()) {
            this.prodstufeId = optionalProdstufeId.get();
            failuresQueryType = BY_FIRMA_ID_PRODSTUFE_ID;
        }
        if (optionalPaNrId.isPresent()) {
            failuresQueryType = BY_PA_NR;
            this.paNrId = optionalPaNrId.get();
        }
    }

    public void setBuendelBc(Optional<String> optionalBuendelBc) {
        if (optionalBuendelBc.isPresent()) {
            this.buendelBc = optionalBuendelBc.get();
        }
    }

    public void setTopParams(Optional<String> optionalAbfallId, Optional<String> optionalWithStueckNr, Optional<String> optionalCount) {
        failuresQueryType = BY_TOP;
        if (optionalAbfallId.isPresent()) {
            this.abfallId = optionalAbfallId.get();
        }
        try {
            if (optionalWithStueckNr.isPresent()) {
                this.withStueckNr = Boolean.parseBoolean(optionalWithStueckNr.get());
            }
            if (optionalCount.isPresent()) {
                this.count = Integer.parseInt(optionalCount.get());
            } else {
                count = DEFAULT_COUNT;
            }
        } catch (NumberFormatException nfe) {
            throw new IlegalParamException(String.format("Invalid withStueckNr (%s) or count (%s)", optionalCount.get()));
        }
    }

    public void setPersonalParams(Optional<String> optionalHours, Optional<String> optionalPersonalId) {
        try {
            if (optionalHours.isPresent()) {
                this.hours = Integer.parseInt(optionalHours.get());
            }
            if (optionalPersonalId.isPresent()) {
                failuresQueryType = BY_PERSONAL;
                this.personalId = Integer.parseInt(optionalPersonalId.get());
            }
        } catch (NumberFormatException nfe) {
            throw new IlegalParamException(String.format("Invalid hours (%s) or personalId (%s)", hours, personalId));
        }
    }
}
