package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class IllegalCountException extends AbstractThrowableProblem {
    public IllegalCountException(Object count) {
        super(URI.create("/api/failuresv2"), "Illegal count", Status.NOT_ACCEPTABLE, String.format("Invalid count (%d)", count));
    }
}
