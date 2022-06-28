package pdc.failures;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pdc.dtos.*;
import pdc.model.*;
import pdc.erp.persistence.ErpPersonalRepositoryDouble;
import pdc.repositories.*;
import pdc.services.ErpMasterFilesService;
import pdc.services.ErpWorkOrdersService;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ErpServiceIT {
    private static final int TRANSFER_WAIT_MINUTES = 5;
    @Autowired
    ErpMasterFilesService service;

    @Autowired
    ErpWorkOrdersService erpWorkOrdersService;

    @Autowired
    ErpTransferRepository erpTransferRepository;


    @BeforeEach
    void init() {
        startTransferifNeccessary();
    }

    @Transactional
    private void startTransferifNeccessary() {
        Optional<ErpTransfer> last = service.findLastRunningTransfer();
        Optional<ErpTransfer> lastCompleted = service.findLastCompletedTransfer();
        if (lastCompleted.isEmpty()) {
            if (last.isEmpty()) {
                service.deleteAllTransfers();
                service.transferDataFromErp();
            }
            waitForTransferFinished();
        }
    }

    void waitForTransferFinished() {
        Optional<ErpTransfer> last = service.findLastRunningTransfer();
        LocalDateTime endOfWait = LocalDateTime.now().plusMinutes(TRANSFER_WAIT_MINUTES);
        if (last.isPresent()) {
            endOfWait = last.get().getStartedAt().plusMinutes(TRANSFER_WAIT_MINUTES);
        }
        Optional<ErpTransfer> lastCompleted;
        do {
            lastCompleted = service.findLastCompletedTransfer();
        } while (lastCompleted.isEmpty() && LocalDateTime.now().isBefore(endOfWait));
        assertTrue(lastCompleted.isPresent());
    }

    @Test
    void testCleanData() {
//        service.cleanErpData();
//        assertThat(service.listAllActiveEmployees(5)).hasSize(0);
//        assertThat(service.listAllActiveWorkgroups(5)).hasSize(0);
//        assertThat(service.listAllfailureCodes(2)).hasSize(0);
//        assertThat(erpWorkOrdersService.listAllMatchingWorkorders(new WorkOrderParams(2, 0, 0))).hasSize(0);
    }

    @Test
    void testTransferAndListAllActiveEmployees() {
        List<PersonalDto> list = service.listAllActiveEmployees(5);
        assertEquals(ErpPersonalRepositoryDouble.GENERATED_NAMES_COUNT, list.size());
    }

    @Test
    void testTransferAndListAllActiveAbfallcodes() {
        waitForTransferFinished();
        List<AbfallcodeDto> list2 = service.listAllfailureCodes(2);
        List<AbfallcodeDto> list5 = service.listAllfailureCodes(5);
        assertEquals(50, list2.size());
        assertEquals(34, list5.size());
    }

    @Test
    void testTransferAndListAllActiveProdauftrag() {
        waitForTransferFinished();
        List<ProdauftragDto> list2 = erpWorkOrdersService.listAllMatchingWorkorders(new WorkOrderParams(2, 0, 0));
        List<ProdauftragDto> list5 = erpWorkOrdersService.listAllMatchingWorkorders(new WorkOrderParams(5, 0, 0));
        assertEquals(155, list2.size());
        assertEquals(374, list5.size());
    }

    @Test
    void testTransferAndListAllActiveSchichtplangruppe() {
        waitForTransferFinished();
        List<SchichtplangruppeDto> list5 = service.listAllActiveWorkgroups(5);
        assertEquals(51, list5.size());
    }

    @Test
    void testFindByFirmaIdAndProdstufeIdAndPaNrId() {
        waitForTransferFinished();
        ProdauftragDto prodauftrag = erpWorkOrdersService.findWorkorder(new WorkOrderParams(5, 50, 69998));
        assertEquals(69998, prodauftrag.getPaNrId());
        assertEquals(5, prodauftrag.getFirmaId());
        assertEquals(50, prodauftrag.getProdstufeId());
    }

    @Test
    void testListAllActiveWorkordersByStueckNrBc() {
        waitForTransferFinished();
        WorkOrderParams param = new WorkOrderParams(5, 0, 0);
        param.setStueckNrBc(Optional.of("4846581/11"));
        List<ProdauftragDto> list = erpWorkOrdersService.listAllMatchingWorkorders(param);
        assertThat(list).extracting(ProdauftragDto::getPaNrId)
                .containsOnly(38499);
    }

    @Test
    void testListAllActiveWorkordersByBuendel() {
        waitForTransferFinished();
        WorkOrderParams param = new WorkOrderParams(5, 50, 0);
        param.setStapelBuendel(Optional.of("102191"), Optional.of("1"), Optional.of("1"), Optional.of("0"));
        List<ProdauftragDto> list = erpWorkOrdersService.listAllMatchingWorkorders(param);
        assertThat(list).extracting(ProdauftragDto::getPaNrId).containsOnly(64156);
    }
}
