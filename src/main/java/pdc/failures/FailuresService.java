package pdc.failures;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.FailureDTO;
import pdc.failures.exceptions.AbfallcodeNotFoundException;
import pdc.failures.exceptions.PersonalNotFoundException;
import pdc.failures.exceptions.ProdauftragNotFoundException;
import pdc.failures.exceptions.SchichtplangruppeNotFoundException;
import pdc.model.*;
import pdc.repositories.AbfallcodeRepository;
import pdc.repositories.PersonalRepository;
import pdc.repositories.ProdauftragRepository;
import pdc.repositories.SchichtplangruppeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class FailuresService {
    private final ModelMapper modelMapper;
    private final failureRepository failureRepository;
    private final ProdauftragRepository prodauftragRepository;
    private final PersonalRepository personalRepository;
    private final AbfallcodeRepository abfallcodeRepository;
    private final SchichtplangruppeRepository schichtplangruppeRepository;
    @Transactional
    public FailureDTO createFailure(CreateFailureCommand command) {
        Optional<Prodauftrag> optionalProdauftrag = prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(command.getFirmaId(), command.getProdstufeId(), command.getPaNrId());
        if (optionalProdauftrag.isEmpty() || !optionalProdauftrag.get().isAktiv()) {
            throw new ProdauftragNotFoundException(command.getFirmaId(), command.getProdstufeId(), command.getPaNrId());
        }
        Personal personalQc = findPersonal("personalQc", command.getPersonalQc());
        Personal personalQc2 = null;
        Personal personalId = null;
        Abfallcode abfallcode = findAbfallcode(command.getFirmaId(), command.getProdstufeId(), command.getAbfallId());
        if (command.getPersonalQc2() > 0) {
            personalQc2 = findPersonal("personalQc2", command.getPersonalQc2());
        }
        if (command.getPersonalId() > 0) {
            personalId = findPersonal("personalId", command.getPersonalId());
        }
        Failure failure = new Failure(optionalProdauftrag.get());
        failure.setAbfallcode(abfallcode);
        failure.setPersonalQc(personalQc);
        if (personalQc2 != null) {
            failure.setPersonalQc2(personalQc2);
        }
        if (personalId != null) {
            failure.setPersonal(personalId);
        }
        if (command.getSchichtplangruppeId() > 0) {
            Optional<Schichtplangruppe> optionalSchichtplangruppe = schichtplangruppeRepository.findById(command.getSchichtplangruppeId());
            if (optionalSchichtplangruppe.isEmpty()) {
                throw new SchichtplangruppeNotFoundException(command.getSchichtplangruppeId());
            }
            failure.setSchichtplangruppe(optionalSchichtplangruppe.get());
        }
        failure.setBuendelBc(command.getBuendelBc());
        failure.setTsErfassung(command.getTsErfassung());
        failure.setPruefung2(command.getPruefung2());
        failureRepository.save(failure);
        return modelMapper.map(failure, pdc.dtos.FailureDTO.class);
    }

    private Abfallcode findAbfallcode(int firmaId, int prodstufeId, String abfallId) {
        Optional<Abfallcode> optionalAbfallcode = abfallcodeRepository.findByFirmaIdAndProdstufeIdAndAbfallIdWithAktiv(firmaId, prodstufeId, abfallId, true);
        if (optionalAbfallcode.isEmpty()) {
            throw new AbfallcodeNotFoundException(firmaId, prodstufeId, abfallId);
        }
        return optionalAbfallcode.get();
    }

    private Personal findPersonal(String attribute, int id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isEmpty()) {
            throw new PersonalNotFoundException("personalQc", id);
        }
        return optionalPersonal.get();
    }

    public List<FailureDTO> findFailures() {
        List<Failure> list = failureRepository.findAll();
        return list.stream()
                .map(failure -> modelMapper.map(failure, FailureDTO.class))
                .toList();
    }
}
