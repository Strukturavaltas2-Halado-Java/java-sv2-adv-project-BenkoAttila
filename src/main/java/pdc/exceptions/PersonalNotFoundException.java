package pdc.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PersonalNotFoundException extends AbstractThrowableProblem {
    public PersonalNotFoundException(String attribute, int id) {
        super(URI.create("failures/personal-not-found"), "Personal not found", Status.NOT_FOUND, String.format("%s personal not found with id:%d", attribute, id));
    }
}
