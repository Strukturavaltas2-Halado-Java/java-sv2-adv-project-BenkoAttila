package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.Optional;

public class StapeBuendelFormatException extends AbstractThrowableProblem {
    public StapeBuendelFormatException(Optional<String> stapelId, Optional<String> buendel1, Optional<String> buendel2, Optional<String> buendel3, String message) {
        super(URI.create("erp/work-orders/wrong-params"),"Wrong param format", Status.NOT_ACCEPTABLE, String.format("Invalid stapelId (%s) or buendels (%s,%s,%s), exception message:%s", stapelId,
                buendel1, buendel2, buendel3, message));
    }
}
