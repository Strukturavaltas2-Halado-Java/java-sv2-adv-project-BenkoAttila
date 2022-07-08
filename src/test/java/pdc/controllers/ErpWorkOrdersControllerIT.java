package pdc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import pdc.dtos.ProdauftragDto;
import pdc.model.ErpTransfer;
import pdc.model.Prodauftrag;
import pdc.services.ErpMasterFilesService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErpWorkOrdersControllerIT {
    private static final int TRANSFER_WAIT_MINUTES = 5;
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ErpMasterFilesService erpMasterFilesService;

    @BeforeEach
    void init() {
        erpMasterFilesService.transferDataFromErp();
        waitForTransferFinished();
    }

    void waitForTransferFinished() {
        Optional<ErpTransfer> last = erpMasterFilesService.findLastRunningTransfer();
        LocalDateTime endOfWait = LocalDateTime.now().plusMinutes(TRANSFER_WAIT_MINUTES);
        if (last.isPresent()) {
            endOfWait = last.get().getStartedAt().plusMinutes(TRANSFER_WAIT_MINUTES);
        }
        Optional<ErpTransfer> lastCompleted;
        do {
            lastCompleted = erpMasterFilesService.findLastCompletedTransfer();
        } while (lastCompleted.isEmpty() && LocalDateTime.now().isBefore(endOfWait));
        assertTrue(lastCompleted.isPresent());
    }
@Test
    void listAllWorkordersByFirmaId2() {
    List<Prodauftrag> list = webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-orders")
                    .queryParam("disableTransferFromErp", true)
                    .build(2))
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Prodauftrag.class).returnResult().getResponseBody();
        assertThat(list).hasSize(155).extracting(Prodauftrag::getFirmaId).containsOnly(2);
    }

    @Test
    void listAllWorkordersByFirmaId5() {
        List<Prodauftrag> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-orders")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Prodauftrag.class).returnResult().getResponseBody();
        assertThat(list).hasSize(374).extracting(Prodauftrag::getFirmaId).containsOnly(5);
    }

    @Test
    void findWorkordersByFirmaIdProdstufeIdPaNrId() {
        Prodauftrag prodauftrag = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-orders/{prodstufeId}/{paNrId}")
                        .queryParam("disableTransferFromErp", true)
                        .build(5, 50, 69998))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Prodauftrag.class).returnResult().getResponseBody();
        assertEquals(69998, prodauftrag.getPaNrId());
        assertEquals(5, prodauftrag.getFirmaId());
        assertEquals(50, prodauftrag.getProdstufeId());
    }

    @Test
    void testListAllActiveWorkordersByStueckNrBc() {
        List<ProdauftragDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-orders")
                        .queryParam("disableTransferFromErp", true)
                        .queryParam("stueckNrBc","4846581/11")
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProdauftragDto.class).returnResult().getResponseBody();

        assertThat(list).extracting(ProdauftragDto::getPaNrId)
                .containsOnly(38499);
    }

    @Test
    void testListAllActiveWorkordersByBuendel() {
        List<ProdauftragDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-orders")
                        .queryParam("disableTransferFromErp", true)
                        .queryParam("stapelId","102191")
                        .queryParam("buendel1", 1)
                        .queryParam("buendel2", 1)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProdauftragDto.class).returnResult().getResponseBody();

        assertThat(list).extracting(ProdauftragDto::getPaNrId).containsOnly(64156);
    }
}