package pdc.failures.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SchichtplangruppeNotFoundException extends AbstractThrowableProblem {
    public SchichtplangruppeNotFoundException(int schichtplangruppeId) {
        super(URI.create("failures/invalid-schichtplangruppe"),"Invalid Schichtplangruppe", Status.NOT_FOUND, String.format("Schichtplangruppe not found with Id:%d",
                schichtplangruppeId));
    }

}
