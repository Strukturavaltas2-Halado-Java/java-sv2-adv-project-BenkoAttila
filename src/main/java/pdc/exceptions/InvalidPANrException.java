package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class InvalidPANrException extends AbstractThrowableProblem {
    public InvalidPANrException(int firmaId, int prodstufeId, int paNrId) {
        super(URI.create("/api/failuresv2/invalid-pa-nr"), "Invalid PA NR", Status.NOT_ACCEPTABLE, String.format("Invalid firmaId (%d), prodstufeId (%d) or paNrId (%d)",firmaId, prodstufeId, paNrId));
    }
}
