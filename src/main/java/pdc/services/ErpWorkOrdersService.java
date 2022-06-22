package pdc.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pdc.dtos.*;
import pdc.erp.persistence.*;
import pdc.exceptions.ProdauftragNotFoundException;
import pdc.model.*;
import pdc.repositories.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class ErpWorkOrdersService {
    private static final int WAIT_BEFORE_NEW_COPY = 90;
    private static final int COPY_TIMEOUT = 5;
    private final ModelMapper modelMapper;
    private final ErpAbfallcodeRepositoryDouble erpAbfallcodeRepository;
    private final AbfallcodeRepository abfallcodeRepository;
    private final ErpTransferRepository erpTransferRepository;
    private final ErpLagerbestRepositoryDouble erpLagerbestRepository;

    private final LagerbestRepository lagerbestRepository;

    private final ErpPersonalRepositoryDouble erpPersonalRepositoryDouble;
    private final PersonalRepository personalRepository;

    private final ErpSchichtplangruppeRepositoryDouble erpSchichtplangruppeRepositoryDouble;
    private final SchichtplangruppeRepository schichtplangruppeRepository;

    private final ErpProdauftragRepositoryDouble erpProdauftragRepositoryDouble;
    private final ProdauftragRepository prodauftragRepository;

    private final ErpProdauftragbuendelRepositoryDouble erpProdauftragbuendelRepositoryDouble;

    private final ProdauftragbuendelRepository prodauftragbuendelRepository;


    public List<ProdauftragDto> listAllMatchingWorkorders(@Valid WorkOrderParams param) {
        log.info(param.toString());
        switch (param.getFilterType()) {
            case BY_NR:
                if (param.getPaNrId() > 0) {
                    return prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(param.getFirmaId(), param.getProdstufeId(), param.getPaNrId())
                            .stream()
                            .map(prodauftrag -> modelMapper.map(prodauftrag, ProdauftragDto.class))
                            .toList();
                } else if (param.getProdstufeId() > 0) {
                    return prodauftragRepository.findAllByFirmaIdAndProdstufeIdAndAktiv(param.getFirmaId(), param.getProdstufeId(), true).stream()
                            .map(prodauftrag -> modelMapper.map(prodauftrag, ProdauftragDto.class))
                            .toList();
                } else {
                    return prodauftragRepository.findAllActiveWorkordersByFirmaIdAndAktiv(param.getFirmaId(), true).stream()
                            .map(prodauftrag -> modelMapper.map(prodauftrag, ProdauftragDto.class))
                            .toList();
                }
            case BY_BUENDEL:
                List<Prodauftragbuendel> pabuendellist = prodauftragbuendelRepository.listaAllByStapelIdAndBuendel1AndBuendel2AndBuendel3(param.getStapelId(), param.getBuendel1(), param.getBuendel2(), param.getBuendel3());
                pabuendellist.forEach(p -> log.info(p.toString()));
                return pabuendellist.stream()
                        .filter(prodauftragbuendel -> prodauftragbuendel.getProdauftrag().getFirmaId() == param.getFirmaId())
                        .filter(prodauftragbuendel -> prodauftragbuendel.getStapelId() == param.getStapelId())
                        .filter(prodauftragbuendel -> prodauftragbuendel.getStueckNr() == param.getStueckNr())
                        .filter(prodauftragbuendel -> prodauftragbuendel.getBuendelgruppeId().equals(Integer.toString(param.getBuendel2())))
                        .filter(prodauftragbuendel -> prodauftragbuendel.getStueckTeilung() == param.getStueckTeilung())
                        .map(Prodauftragbuendel::getProdauftrag).map(prodauftrag -> modelMapper.map(prodauftrag, ProdauftragDto.class)).toList();
            case BY_STUECK_NR:
                log.info(String.format("%d, %d", param.getStueckNr(), param.getStueckTeilung()));
                List<Lagerbestdetail> lbdlist = lagerbestRepository.findByStueckNrAndStueckTeilung(param.getStueckNr(), param.getStueckTeilung());
                return lbdlist.stream()
                        .filter(lagerbestdetail -> lagerbestdetail.getStueckNr() == param.getStueckNr())
                        .filter(lagerbestdetail -> lagerbestdetail.getStueckTeilung() == param.getStueckTeilung())
                        .map(Lagerbestdetail::getProdauftrag)
                        .filter(Prodauftrag::isAktiv)
                        .filter(prodauftrag -> prodauftrag.getFirmaId() == param.getFirmaId())
                        .map(prodauftrag -> modelMapper.map(prodauftrag, ProdauftragDto.class)).toList();
            default:
        }
        return new ArrayList<>();
    }

    public ProdauftragDto findWorkorder(WorkOrderParams param) {
        log.info(param.toString());
        Optional<Prodauftrag> optionalProdauftrag = prodauftragRepository.getByFirmaIdAndProdstufeIdAndPaNrIdAndAktiv(param.getFirmaId(), param.getProdstufeId(), param.getPaNrId(), true);
        if (optionalProdauftrag.isEmpty() || !optionalProdauftrag.get().isAktiv()) {
            throw new ProdauftragNotFoundException(param.getFirmaId(), param.getProdstufeId(), param.getPaNrId());
        }
        return modelMapper.map(optionalProdauftrag.get(), ProdauftragDto.class);
    }
}

