package pdc.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import pdc.dtos.ProdauftragDto;
import pdc.model.Abfallcode;
import pdc.model.Prodauftrag;
import pdc.model.WorkOrderParams;
import pdc.services.ErpMasterFilesService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErpWorkOrdersControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ErpMasterFilesService erpMasterFilesService;

    @BeforeEach
    void init() {
        erpMasterFilesService.transferDataFromErp();
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