package pdc.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.FailureDto;
import pdc.dtos.FailureSummarizedByPaAndAbfallcodeDto;
import pdc.dtos.UpdateFailureCommand;
import pdc.exceptions.*;
import pdc.model.*;
import pdc.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FailuresService {
    private final ModelMapper modelMapper;
    private final FailuresRepository failureRepository;
    private final ProdauftragRepository prodauftragRepository;
    private final PersonalRepository personalRepository;
    private final AbfallcodeRepository abfallcodeRepository;
    private final SchichtplangruppeRepository schichtplangruppeRepository;

    @Transactional
    public FailureDto createFailure(CreateFailureCommand command) {
        if (command.getTsErfassung() == null) {
            command.setTsErfassung(LocalDateTime.now());
        }
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
        failure.setMengeAbfall(command.getMengeAbfall());
        failure.setPruefung2(command.getPruefung2());
        failureRepository.save(failure);
        FailureDto dto = modelMapper.map(failure, FailureDto.class);
        log.info(dto.toString());
        return dto;
    }

    private Abfallcode findAbfallcode(int firmaId, int prodstufeId, String abfallId) {
        Optional<Abfallcode> optionalAbfallcode = abfallcodeRepository.findByFirmaIdAndProdstufeIdAndAbfallIdAndAktivTrue(firmaId, prodstufeId, abfallId);
        return optionalAbfallcode.orElseThrow(() -> new AbfallcodeNotFoundException(firmaId, prodstufeId, abfallId));
    }

    private Personal findPersonal(String attribute, int id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        return optionalPersonal.orElseThrow(() -> new PersonalNotFoundException("personalQc", id));
    }

    public List<FailureDto> findFailures(FailuresParams params) {
        List<Failure> failurelist;
        switch (params.getFailuresQueryType()) {
            case BY_PA_NR:
//                pa szám + buendelBC-re szűrés;
                Prodauftrag prodauftrag = prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId()).orElseThrow(() -> new InvalidPANrException(params.getFirmaId(), params.getProdstufeId(), params.getPaNrId()));
                failurelist = failureRepository.findByBuendelBcAndProdauftrag_Id(params.getBuendelBc(), prodauftrag.getId());
                return failurelist.stream()
                        .map(failure -> modelMapper.map(failure, FailureDto.class))
                        .toList();
            case BY_PERSONAL:
//                az utolsó hours (default=12) órában az adott meós (personalQc vagy personalQC2) által rögzített hibák
                LocalDateTime from = LocalDateTime.now().minusHours(params.getHours());
                failurelist = failureRepository.findByPersonalQCFromDateTime(params.getPersonalId(), from);
                return SummarizeByPaAndAbfallId(failurelist);
        }
        throw new IllegalStateException("Invalid query type! " + params.getFailuresQueryType());
    }

    private List<FailureDto> SummarizeByPaAndAbfallId(List<Failure> failurelist) {
        Map<SummaryByWorkOrderAndFailureCodeKey, FailureSummarizedByPaAndAbfallcodeDto> summarized = new HashMap<>();
        SummaryByWorkOrderAndFailureCodeKey key = new SummaryByWorkOrderAndFailureCodeKey();
        for (Failure actual : failurelist) {
            FailureSummarizedByPaAndAbfallcodeDto dto = modelMapper.map(actual, FailureSummarizedByPaAndAbfallcodeDto.class);
            key = modelMapper.map(actual, SummaryByWorkOrderAndFailureCodeKey.class);
            FailureSummarizedByPaAndAbfallcodeDto sum = summarized.get(key);
            if (sum == null) {
                summarized.put(key, dto);
            } else {
                sum.addMengeAbfall(dto.getMengeAbfall());
            }
        }
        return summarized.entrySet().stream()
                .peek(s -> log.info(s.toString()))
                .map(s -> modelMapper.map(s.getValue(), FailureDto.class))
                .toList();
    }

    public List<FailureDto> findTopFailures(FailuresParams params) {
        switch (params.getFailuresQueryType()) {
            case BY_PA_NR:
//                pa szám + buendelBC-re szűrés;
                break;
            case BY_PERSONAL:
//                az utolsó hours (default=12) órában az adott meós (personalQc vagy personalQC2) által rögzített hibák
                break;
            case BY_TOP:
//                  összesített hibék, ah withStueckNr=true, akkorszabászai hibák, ahol a stueck_nr > 0,
//                ha withStueckNr=false akkor minősítési hibák, ahol stueckNr=0;
                break;
        }
        List<Failure> list = failureRepository.findAll();
        return list.stream()
                .map(failure -> modelMapper.map(failure, FailureDto.class))
                .toList();
    }

    public FailureDto findFailure(long id) {
        Optional<Failure> optionalFailure = failureRepository.findById(id);
        return modelMapper.map(optionalFailure.orElseThrow(() -> new FailureNotFoundException(id)), FailureDto.class);
    }

    @Transactional
    public FailureDto updateFailure(long id, UpdateFailureCommand command) {
        Optional<Failure> optionalFailure = failureRepository.findById(id);
        Failure failure = optionalFailure.orElseThrow(() -> new FailureNotFoundException(id));
        failure.setPersonal(findPersonal("personal", command.getPersonalId()));
        return modelMapper.map(failure, FailureDto.class);
    }
}
