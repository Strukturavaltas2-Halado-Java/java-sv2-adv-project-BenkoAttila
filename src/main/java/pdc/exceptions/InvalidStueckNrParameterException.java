package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import pdc.model.WorkOrderParams;

import java.net.URI;

public class InvalidStueckNrParameterException extends AbstractThrowableProblem {
    public InvalidStueckNrParameterException(WorkOrderParams params) {
        super(URI.create("work-orders/illegal-parameters"), "Illegal parameters", Status.NOT_ACCEPTABLE, String.format("Invalid StueckNr (should be greater than 1000000) or StueckTeilung (should be between 0 and 1000)", params.getStueckNr(), params.getStueckTeilung()));
    }
}
