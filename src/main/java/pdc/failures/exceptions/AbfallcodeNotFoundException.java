package pdc.failures.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class AbfallcodeNotFoundException extends AbstractThrowableProblem {
    public AbfallcodeNotFoundException(int firmaId, int prodstufeId, String abafllId) {
        super(URI.create("failures/abfallcode-not-found"), "Abfallcode not found", Status.NOT_FOUND, String.format("Abfallcode not found with firmaId:%d, prodstufeId:%d, abfallId:%s", firmaId, prodstufeId, abafllId));
    }
}
