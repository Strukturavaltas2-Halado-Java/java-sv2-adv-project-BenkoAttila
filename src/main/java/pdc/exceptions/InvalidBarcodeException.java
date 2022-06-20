package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.Optional;

public class InvalidBarcodeException extends AbstractThrowableProblem {
    public InvalidBarcodeException(String stueckNrBc, String message) {
        super(URI.create("erp/work-orders/wrong-stueckNrBc"),"Wrong barcode format", Status.NOT_ACCEPTABLE, String.format("Invalid barcode (%s) exception message:%s!", stueckNrBc, stueckNrBc, message));
    }
}
