package pdc.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pdc.dtos.*;
import pdc.erp.persistence.*;
import pdc.model.*;
import pdc.repositories.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class ErpMasterFilesService {
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


    public List<AbfallcodeDto> listAllfailureCodes(int companyId) {
        return erpAbfallcodeRepository.findAll().filter(abfallcode -> abfallcode.getFirmaId() == companyId).map(abfallcode -> modelMapper.map(abfallcode, AbfallcodeDto.class)).toList();
    }

    public List<ErpTransferDto> listAllErpTransfers() {
        return erpTransferRepository.findAll().stream().map(erpTransfer -> modelMapper.map(erpTransfer, ErpTransferDto.class)).toList();
    }


    public void transferDataFromErp() {
        Integer currentTransferId = null;
        if (shouldStartTransfer()) {
            ErpTransfer saved = erpTransferRepository.save(new ErpTransfer(LocalDateTime.now()));
            currentTransferId = saved.getId();
        }
        if (currentTransferId != null) {
            saveErpData(currentTransferId);
        }
    }

    public boolean shouldStartTransfer() {
        Optional<ErpTransfer> lastRunning = erpTransferRepository.findRunningOrderByIdDesc();
        Optional<ErpTransfer> lastCompleted = erpTransferRepository.findCompletedOrderByIdDesc();
        if ((erpTransferRepository.findAll().isEmpty() || !lastCompleted.isPresent()) && (!lastRunning.isPresent() || lastRunning.get().timedOut(COPY_TIMEOUT))) {
            return true;
        }
        if (lastCompleted.isPresent() && lastCompleted.get().timedOut(WAIT_BEFORE_NEW_COPY) && (!lastRunning.isPresent() || lastRunning.get().timedOut(COPY_TIMEOUT))) {
            return true;
        }
        return false;
    }

    public Optional<ErpTransfer> findLastCompletedTransfer() {
        return erpTransferRepository.findCompletedOrderByIdDesc();
    }

    public Optional<ErpTransfer> findLastRunningTransfer() {
        return erpTransferRepository.findRunningOrderByIdDesc();
    }

    @Transactional
    public void deleteAllTransfers() {
        cleanErpData();
        erpTransferRepository.deleteAll();
    }

    @Transactional
    public ErpTransfer createTransfer(LocalDateTime startedAt) {
        ErpTransfer erpTransfer = new ErpTransfer(startedAt);
        erpTransferRepository.save(erpTransfer);
        return erpTransfer;
    }

    @Transactional
    public ErpTransfer closeTransferById(int id) {
        ErpTransfer erpTransfer = erpTransferRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Can not find transfer with id:%d", id)));
        erpTransfer.setFinishedAt(LocalDateTime.now());
        erpTransferRepository.save(erpTransfer);
        return erpTransfer;
    }


    @Transactional
    public void saveErpData(Integer currentTransferId) {
        cleanErpData();
        saveAbfallCodeFromErp();
        saveProdauftragFromErp();
        savePersonalFromErp();
        saveSchichtlplangruppeFromErp();
        saveLagerbestFromErp();
        saveProdauftragbuendelFromErp();
        if (currentTransferId != null) {
            closeTransferById(currentTransferId);
        }
    }

    @Transactional
    public void cleanErpData() {
        inactivateAbfallCodes();
        inactivatePersonal();
        lagerbestRepository.deleteAll();
        inactivatSchichtplangruppen();
        prodauftragbuendelRepository.deleteAll();
        inactivateErpprodauftragen();
    }

    @Transactional
    private void inactivateErpprodauftragen() {
        List<Prodauftrag> list = prodauftragRepository.findAll();
        for (Prodauftrag prodauftrag : list) {
            prodauftrag.setAktiv(false);
        }
//        prodauftragRepository.inactivateAll();
    }

    @Transactional
    private void inactivatSchichtplangruppen() {
        List<Schichtplangruppe> list = schichtplangruppeRepository.findAll();
        for (Schichtplangruppe schichtplangruppe : list) {
            schichtplangruppe.setAktiv(false);

        }
//        schichtplangruppeRepository.inactivateAll();
    }

    private void saveProdauftragFromErp() {
        List<pdc.erp.model.Prodauftrag> list = erpProdauftragRepositoryDouble.findAll().toList();
        for (pdc.erp.model.Prodauftrag actual : list) {
            Prodauftrag prodauftrag = null;
            Optional<Prodauftrag> optionalProdauftrag = prodauftragRepository.getByFirmaIdAndProdstufeIdAndPaNrId(actual.getFirmaId(), actual.getProdstufeId(), actual.getPaNrId());
            if (optionalProdauftrag.isEmpty()) {
                prodauftrag = new Prodauftrag(actual.getFirmaId(), actual.getProdstufeId(), actual.getPaNrId());
            } else {
                prodauftrag = optionalProdauftrag.get();
            }
            prodauftrag.setArtikelId(actual.getProdauftragartikel().getArtikelId());
            prodauftrag.setAktiv(actual.isAktiv());
            prodauftrag.setFarbeId(actual.getProdauftragartikel().getFarbeId());
            prodauftrag.setFertigungszustandId(actual.getProdauftragartikel().getFertigungszustandId());
            prodauftrag.setVarianteId(actual.getProdauftragartikel().getVarianteId());
            prodauftrag.setGroesseId(actual.getProdauftragartikel().getGroesseId());
            prodauftrag.setKennzPartiewechsel(actual.getKennzPartiewechsel());
            prodauftrag.setMenge(actual.getMenge());
            prodauftragRepository.save(prodauftrag);
        }
    }

    @Transactional
    public void saveProdauftragbuendelFromErp() {
        int count = 0;
        List<pdc.erp.model.Prodauftragbuendel> list = erpProdauftragbuendelRepositoryDouble.findAll().toList();
        for (pdc.erp.model.Prodauftragbuendel actual : list) {
            Optional<Prodauftrag> prodauftrag = prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(actual.getFirmaId(), actual.getProdstufeId(), actual.getPaNrId());
            if (prodauftrag.isPresent()) {
                Prodauftragbuendel prodauftragbuendel = new Prodauftragbuendel();
                prodauftragbuendel.setStapelId(actual.getStapelId());
                prodauftragbuendel.setProdauftrag(prodauftrag.get());
                prodauftragbuendel.setKartonNrId(actual.getKartonNrId());
                prodauftragbuendel.setStueckNr(actual.getStueckNr());
                prodauftragbuendel.setBuendelgruppeId(actual.getBuendelgruppeId());
                prodauftragbuendel.setStueckTeilung(actual.getStueckTeilung());
                prodauftragbuendelRepository.save(prodauftragbuendel);
            } else {
                throw new IllegalStateException(actual.toString());
            }
        }
    }

    void saveSchichtlplangruppeFromErp() {
        List<pdc.erp.model.Schichtplangruppe> list = erpSchichtplangruppeRepositoryDouble.findAll(5).toList();
        for (pdc.erp.model.Schichtplangruppe actual : list) {
            Schichtplangruppe schichtplangruppe;
            Optional<Schichtplangruppe> optionalSchichtplangruppe = schichtplangruppeRepository.findById(actual.getSchichtplangruppeId());
            if (optionalSchichtplangruppe.isPresent()) {
                schichtplangruppe = optionalSchichtplangruppe.get();
            } else {
                schichtplangruppe = new Schichtplangruppe(actual.getSchichtplangruppeId());
            }
            schichtplangruppe.setAktiv(true);
            schichtplangruppe.setFirmaId(actual.getFirmaId());
            schichtplangruppe.setSchichtplangruppeBez(actual.getSchichtplangruppeBez());
            schichtplangruppeRepository.save(schichtplangruppe);
        }
    }

    @Transactional
    void inactivatePersonal() {
        List<Personal> list = personalRepository.findAll();
        for (Personal personal : list) {
            personal.setAktiv(false);
        }
//        personalRepository.inactivateAll();
    }

    public void savePersonalFromErp() {
        List<pdc.erp.model.Personal> list = erpPersonalRepositoryDouble.findAll(5).toList();
        for (pdc.erp.model.Personal actual : list) {
            log.info(actual.toString());
            Personal personal;
            Optional<Personal> optionalPersonal = personalRepository.findById(actual.getPersonalId());
            if (optionalPersonal.isPresent()) {
                log.info(String.format("Personal found: %d", actual.getPersonalId()));
                personal = optionalPersonal.get();
                personal.setFirmaId(actual.getFirmaId());
                personal.setPersName(actual.getPersName());
                personal.setAktiv(actual.isAktiv());

            } else {
                log.info(String.format("Personal not found: %d", actual.getPersonalId()));
                personal = new Personal(actual.getPersonalId());
                personal.setFirmaId(actual.getFirmaId());
                personal.setPersName(actual.getPersName());
                personal.setAktiv(actual.isAktiv());
            }
            personalRepository.save(personal);
        }
    }


    public void saveLagerbestFromErp() {
        List<pdc.erp.model.Lagerbestdetail> list = erpLagerbestRepository.findAllDetail().toList();
        for (pdc.erp.model.Lagerbestdetail actual : list) {
            System.out.println(actual);
            Optional<Prodauftrag> optionalProdauftrag = prodauftragRepository.findByFirmaIdAndProdstufeIdAndPaNrId(actual.getLagerbestglobal().getFirmaId(), actual.getProdstufeId(), actual.getPaNrId());
            if (optionalProdauftrag.isPresent()) {
                Lagerbestdetail lbdetail = new Lagerbestdetail(actual.getStueckNr(), actual.getStueckTeilung());
                lbdetail.setProdauftrag(optionalProdauftrag.get());
                lbdetail.setArtikelId(actual.getLagerbestglobal().getArtikelId());
                lbdetail.setFarbeId(actual.getLagerbestglobal().getFarbeId());
                lbdetail.setVarianteId(actual.getLagerbestglobal().getVarianteId());
                lbdetail.setFertigungszustandId(actual.getLagerbestglobal().getFertigungszustandId());
                lagerbestRepository.save(lbdetail);
            }
        }
    }

    private void inactivateAbfallCodes() {
        abfallcodeRepository.inactivateAll();
    }

    void saveAbfallCodeFromErp() {
        List<pdc.erp.model.Abfallcode> list = erpAbfallcodeRepository.findAll().toList();
        for (pdc.erp.model.Abfallcode actual : list) {
            Abfallcode abfallcode;
            Optional<Abfallcode> optionalAbfallcode = abfallcodeRepository.findByFirmaIdAndProdstufeIdAndAbfallId(actual.getFirmaId(), actual.getProdstufeId(), actual.getAbfallId());
            if (optionalAbfallcode.isPresent()) {
                abfallcode = optionalAbfallcode.get();
            } else {
                abfallcode = new Abfallcode(actual.getFirmaId(), actual.getProdstufeId(), actual.getAbfallId());
            }
            abfallcode.setAktiv(true);
            abfallcode.setFehlerGruppeId(actual.getFehlerGruppeId());
            abfallcode.setAbfallText(actual.getAbfallText());
            abfallcodeRepository.save(abfallcode);
        }
    }

    public List<AbfallcodeDto> listAllActivefailurecodes(int firmaId) {
        return abfallcodeRepository.findByFirmaWithAktivTrue(firmaId, true)
                .stream()
                .map(abfallcode -> modelMapper.map(abfallcode, AbfallcodeDto.class))
                .toList();
    }


    public List<PersonalDto> listAllActiveEmployees(int firmaId) {
        return personalRepository.findByFirmaIdEqualsAndAktivIsTrue(firmaId).stream()
                .map(sg -> modelMapper.map(sg, PersonalDto.class))
                .toList();
    }

    public List<SchichtplangruppeDto> listAllActiveWorkgroups(int firmaId) {
        return schichtplangruppeRepository.findByFirmaIdIsAndAktivTrue(firmaId).stream()
                .map(sg -> modelMapper.map(sg, SchichtplangruppeDto.class))
                .toList();
    }
}

