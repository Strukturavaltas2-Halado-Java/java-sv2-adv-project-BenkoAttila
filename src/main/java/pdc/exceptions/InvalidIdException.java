package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class InvalidIdException extends AbstractThrowableProblem {
    public InvalidIdException(int id) {
        super(URI.create("api/failuresv2/invalid-id"), "Invalid id", Status.NOT_ACCEPTABLE, String.format("Invalid id. %d", id));
    }
}
