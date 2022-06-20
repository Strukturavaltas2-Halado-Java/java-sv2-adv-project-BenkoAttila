package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import pdc.model.WorkOrderParams;

import java.net.URI;

public class PaNrShouldBeZeroWhenFiletringException extends AbstractThrowableProblem {
    public PaNrShouldBeZeroWhenFiletringException(WorkOrderParams params) {
        super(URI.create("work-orders/illegal-parameters"), "Illegal parameters", Status.NOT_ACCEPTABLE, String.format("PaNrId(%d) should be zero when filtering by StueckNr(%d) or Buendels(stapel=%d)!", params.getPaNrId(), params.getStueckNr(), params.getStapelId()));
    }
}
