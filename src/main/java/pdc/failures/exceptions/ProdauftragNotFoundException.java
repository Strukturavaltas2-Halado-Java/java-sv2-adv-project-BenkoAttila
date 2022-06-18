package pdc.failures.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import pdc.model.ProdauftragId;

import java.net.URI;

public class ProdauftragNotFoundException extends AbstractThrowableProblem {
    public ProdauftragNotFoundException(ProdauftragId prodauftragId) {
        super(URI.create("failures/invalid-prodauftrag"),"Invalid Prodauftrag", Status.NOT_FOUND, String.format("Prodauftrag not found with firmaId:%d, prodstufeId:%d, paNrId:%d",
                prodauftragId.getFirmaId(),
                prodauftragId.getProdstufeId(),
                prodauftragId.getPaNrId()));
    }
}
