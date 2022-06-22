package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class IlegalParamException extends AbstractThrowableProblem {
    public IlegalParamException(String message) {
        super(URI.create("/api/failuresv2/illegal-param"), "Illegal Parameter", Status.NOT_ACCEPTABLE, message);
    }
}
