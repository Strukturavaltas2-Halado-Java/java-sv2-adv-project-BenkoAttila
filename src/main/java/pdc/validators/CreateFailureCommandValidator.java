package pdc.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pdc.dtos.CreateFailureCommand;
import pdc.exceptions.AbfallcodeNotFoundException;
import pdc.exceptions.PersonalNotFoundException;
import pdc.exceptions.ProdauftragNotFoundException;
import pdc.exceptions.SchichtplangruppeNotFoundException;
import pdc.model.*;
import pdc.repositories.AbfallcodeRepository;
import pdc.repositories.PersonalRepository;
import pdc.repositories.ProdauftragRepository;
import pdc.repositories.SchichtplangruppeRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateFailureCommandValidator {
    private final ProdauftragRepository prodauftragRepository;
    private final PersonalRepository personalRepository;
    private final AbfallcodeRepository abfallcodeRepository;
    private final SchichtplangruppeRepository schichtplangruppeRepository;

    public void validateCommand(CreateFailureCommand command) {
        Optional<Prodauftrag> optionalProdauftrag = prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(command.getFirmaId(), command.getProdstufeId(), command.getPaNrId());
        if (optionalProdauftrag.isEmpty() || !optionalProdauftrag.get().isAktiv()) {
            throw new ProdauftragNotFoundException(command.getFirmaId(), command.getProdstufeId(), command.getPaNrId());
        }
        validatePersonal("personalQc", command.getPersonalQc());
        validateAbfallcode(command.getFirmaId(), command.getProdstufeId(), command.getAbfallId());
        if (command.getPersonalQc2() > 0) {
            validatePersonal("personalQc2", command.getPersonalQc2());
        }
        if (command.getPersonalId() > 0) {
            validatePersonal("personalId", command.getPersonalId());
        }
        if (command.getSchichtplangruppeId() > 0) {
            validateSchchtplangruppe(command.getSchichtplangruppeId());
        }
    }

    private void validateSchchtplangruppe(int id) {
        Optional<Schichtplangruppe> optionalSchichtplangruppe = schichtplangruppeRepository.findById(id);
        if (optionalSchichtplangruppe.isEmpty()) {
            throw new SchichtplangruppeNotFoundException(id);
        }
    }
    private void validateAbfallcode(int firmaId, int prodstufeId, String abfallId) {
        Optional<Abfallcode> optionalAbfallcode = abfallcodeRepository.findByFirmaIdAndProdstufeIdAndAbfallIdWithAktiv(firmaId, prodstufeId, abfallId, true);
        if (optionalAbfallcode.isEmpty()) {
            throw new AbfallcodeNotFoundException(firmaId, prodstufeId, abfallId);
        }
    }

    private void validatePersonal(String attribute, int id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isEmpty()) {
            throw new PersonalNotFoundException(attribute, id);
        }
    }
}
