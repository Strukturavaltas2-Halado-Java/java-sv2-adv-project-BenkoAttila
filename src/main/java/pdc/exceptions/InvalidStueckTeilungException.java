package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class InvalidStueckTeilungException extends AbstractThrowableProblem {
    public InvalidStueckTeilungException(Integer stueckNr, Integer stueckTeilung) {
        super(URI.create("api/failures/invalid-stueck-teilung"), "Invalid Stueck Teilung!", Status.NOT_ACCEPTABLE, String.format("Invalid Stueck Teilung: %d for Stueck Nr: %d", stueckTeilung, stueckNr));
    }
}
