package pdc.failures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pdc.dtos.*;
import pdc.model.*;
import pdc.erp.persistence.ErpPersonalRepositoryDouble;
import pdc.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ErpServiceIntegrationTest {
    @Autowired
    ErpService service;

    @Autowired
    ErpTransferRepository erpTransferRepository;

    @BeforeEach
    void init() {
        service.deleteAllTransfers();
    }

    @Test
    void testCreateAndCloseTransfer() {
        ErpTransfer erpTransfer = service.createTransfer(LocalDateTime.now());
        assertTrue(erpTransfer.getId() > 0);
        System.out.println(erpTransfer);
        service.closeTransferById(erpTransfer.getId());
        erpTransfer = erpTransferRepository.findById(erpTransfer.getId()).get();
        assertThat(erpTransfer.getFinishedAt())
                .isNotNull()
                .isAfter(erpTransfer.getStartedAt().minusMinutes(10));
    }

    @Test
    void testLastCompleted() {
        ErpTransfer first = service.createTransfer(LocalDateTime.now());
        service.closeTransferById(first.getId());
        System.out.println("------------------");
        System.out.println(first);
        ErpTransfer erpTransfer = service.createTransfer(LocalDateTime.now());
        System.out.println(erpTransfer);
        ErpTransfer last = service.findLastCompletedTransfer().get();
        System.out.println(last);
        assertEquals(first.getId(), last.getId());
    }
@Test
    void testLast() {
        ErpTransfer erpTransfer = service.createTransfer(LocalDateTime.now());
        service.closeTransferById(erpTransfer.getId());
        System.out.println("------------------");
        System.out.println(erpTransfer);
        erpTransfer = service.createTransfer(LocalDateTime.now());
        System.out.println(erpTransfer);
        ErpTransfer last = service.findLastRunningTransfer().get();
        System.out.println(last);
        assertEquals(erpTransfer.getId(), last.getId());
    }
    @Test
    void testShouldTransferEmptyTransfers() {
        assertTrue(service.shouldStartTransfer());
    }

    @Test
    void testShouldTransferOldCompletedTransfers() {
        ErpTransfer last = service.createTransfer(LocalDateTime.now().minusHours(10));
        service.closeTransferById(last.getId());
        assertTrue(service.shouldStartTransfer());
    }

    @Test
    void testShouldTransferOldCompletedOldRunningTransfers() {
        ErpTransfer last = service.createTransfer(LocalDateTime.now().minusHours(10));
        service.closeTransferById(last.getId());
        service.createTransfer(LocalDateTime.now().minusMinutes(10));
        assertTrue(service.shouldStartTransfer());
    }

    @Test
    void testShouldTransferNoCompletedOldRunningTransfers() {
        service.createTransfer(LocalDateTime.now().minusMinutes(10));
        assertTrue(service.shouldStartTransfer());
    }
    @Test
    void testShouldTransferOldCompletedNewRunningTransfers() {
        ErpTransfer last = service.createTransfer(LocalDateTime.now().minusHours(10));
        service.closeTransferById(last.getId());
        service.createTransfer(LocalDateTime.now().minusMinutes(1));
        assertFalse(service.shouldStartTransfer());
    }

    @Test
    void testTransferAndListAllActiveEmployees() {
        service.cleanErpData();
        service.transferDataFromErp();
        List<PersonalDTO> list = service.listAllActiveEmployees(5);
        assertThat(list).hasSize(ErpPersonalRepositoryDouble.GENERATED_NAMES_COUNT - ErpPersonalRepositoryDouble.INACTIVE_COUNT);
    }

    @Test
    void testTransferAndListAllActiveAbfallcodes() {
        service.cleanErpData();
        service.transferDataFromErp();
        List<AbfallcodeDTO> list2 = service.listAllfailureCodes(2);
        List<AbfallcodeDTO> list5 = service.listAllfailureCodes(5);
        assertEquals(50, list2.size());
        assertEquals(34, list5.size());
    }

    @Test
    void testTransferAndListAllActiveProdauftrag() {
        service.cleanErpData();
        service.transferDataFromErp();
        List<ProdauftragDTO> list2 = service.listAllActiveWorkorders(2);
        List<ProdauftragDTO> list5 = service.listAllActiveWorkorders(5);
        assertEquals(155, list2.size());
        assertEquals(374, list5.size());
    }

    @Test
    void testTransferAndListAllActiveSchichtplangruppe() {
        service.cleanErpData();
        service.transferDataFromErp();
        List<SchichtplangruppeDTO> list5 = service.listAllActiveWorkgroups(5);
        assertEquals(51, list5.size());
    }

}