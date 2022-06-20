package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import pdc.model.WorkOrderParams;

import java.net.URI;

public class IllegalWorkOrderParamException extends AbstractThrowableProblem {
    public IllegalWorkOrderParamException(WorkOrderParams params) {
        super(URI.create("work-orders/illegal-parameters"),"Illegal parameters", Status.NOT_ACCEPTABLE, String.format("Can not detect filtering type for parameters:%s", params.toString()));
    }
}
