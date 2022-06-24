package pdc.validators;

import pdc.exceptions.IllegalCountException;
import pdc.exceptions.IllegalPersonalIdException;
import pdc.exceptions.InvalidIdException;
import pdc.exceptions.InvalidPANrException;
import pdc.model.FailuresParams;

public class FailuresParamsValidator {
    public void validate(FailuresParams params) {
        switch (params.getFailuresQueryType()) {
            case BY_TOP:
                validateTopFailures(params);
            case BY_FIRMA_ID:
                if (params.getFirmaId() <= 0 || params.getProdstufeId() < 0 || params.getPaNrId() < 0) {
                    throw new InvalidPANrException(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId());
                }
                break;
            case BY_FIRMA_ID_PRODSTUFE_ID:
                if (params.getFirmaId() <= 0 || params.getProdstufeId() <= 0 || params.getPaNrId() < 0) {
                    throw new InvalidPANrException(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId());
                }
                break;
            case BY_PA_NR:
                if (params.getFirmaId() <= 0 || params.getProdstufeId() < 0 || params.getPaNrId() < 0) {
                    throw new InvalidPANrException(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId());
                }
                if (params.getPaNrId() > 0  && params.getProdstufeId() <= 0) {
                    throw new InvalidPANrException(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId());
                }
                break;
            case BY_PERSONAL:
                if (params.getPersonalId() <= 0) {
                    throw new IllegalPersonalIdException(params.getPersonalId());
                }
                break;
            default:
                throw new IllegalStateException("Unhandled filter type!");
        }
    }

    public void validateTopFailures(FailuresParams params) {
        if (params.getCount() <= 0 || params.getCount() > 10) {
            throw new IllegalCountException(params.getCount());
        }
    }
}
