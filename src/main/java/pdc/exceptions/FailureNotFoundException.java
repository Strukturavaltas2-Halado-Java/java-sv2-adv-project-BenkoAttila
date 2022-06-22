package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class FailureNotFoundException extends AbstractThrowableProblem {
    public FailureNotFoundException(long id) {
        super(URI.create("api/failuresv2/not-found"), "Entity not found", Status.NOT_FOUND, String.format("Failure not found with id:%d", id));
    }
}
