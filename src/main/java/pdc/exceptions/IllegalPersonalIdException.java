package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class IllegalPersonalIdException extends AbstractThrowableProblem {
    public IllegalPersonalIdException(int personalId) {
        super(URI.create("/api/failuresv2/illegal-personalid"), "Illegal Personal id", Status.NOT_ACCEPTABLE, String.format("Invalid personalId: %d", personalId));
    }
}
