package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import pdc.model.WorkOrderParams;

import java.net.URI;

public class IllegalBuendelFilteringException extends AbstractThrowableProblem {
    public IllegalBuendelFilteringException(WorkOrderParams params) {
        super(URI.create("work-orders/illegal-parameters"), "Illegal parameters", Status.NOT_ACCEPTABLE, String.format("Invalid stapelId (%d) or buendel1 (%d)!", params.getStapelId(), params.getBuendel1()));
    }
}
