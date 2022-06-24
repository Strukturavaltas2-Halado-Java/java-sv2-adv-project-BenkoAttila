package pdc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import pdc.erp.persistence.ErpPersonalRepositoryDouble;
import pdc.model.Abfallcode;
import pdc.model.ErpTransfer;
import pdc.model.Personal;
import pdc.model.Schichtplangruppe;
import pdc.services.ErpMasterFilesService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from erptransfers"})
class ErpMasterFilesControllerIntegrationTest {
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ErpMasterFilesService erpMasterFilesService;

    @BeforeEach
    void init() {
        erpMasterFilesService.transferDataFromErp();
    }

    @Test
    void listAllfailureCodesFirmaId2() {
        List<Abfallcode> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/failure-codes")
                        .queryParam("disableTransferFromErp", true)
                        .build(2))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Abfallcode.class).returnResult().getResponseBody();
        assertThat(list).hasSize(50).extracting(Abfallcode::getFirmaId).containsOnly(2);
    }

    @Test
    void listAllfailureCodesFirmaId5() {
        List<Abfallcode> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/failure-codes")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Abfallcode.class).returnResult().getResponseBody();
        assertThat(list).hasSize(34).extracting(Abfallcode::getFirmaId).containsOnly(5);
    }

    @Test
    void listAllEmployees() {
        List<Personal> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/employees")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Personal.class).returnResult().getResponseBody();
        assertThat(list).hasSize(ErpPersonalRepositoryDouble.GENERATED_NAMES_COUNT).extracting(Personal::getFirmaId)
                .containsOnly(5);
    }

    @Test
    void listAllWorkgroups() {
        List<Schichtplangruppe> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/work-groups")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Schichtplangruppe.class).returnResult().getResponseBody();
        assertThat(list).hasSize(50).extracting(Schichtplangruppe::getFirmaId)
                .containsOnly(5);
    }

    @Test
    void testListAllERPTransfers() {
        List<ErpTransfer> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp")
                        .queryParam("disableTransferFromErp", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ErpTransfer.class).returnResult().getResponseBody();
        list.forEach(erpTransfer -> log.info(erpTransfer.toString()));
        ErpTransfer last = list.get(list.size() - 1);
        if (last.getFinishedAt() != null) {
            assertThat(last.getFinishedAt()).isAfter(last.getStartedAt());
        }
    }
}