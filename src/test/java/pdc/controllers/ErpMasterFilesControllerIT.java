package pdc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import pdc.dtos.AbfallcodeDto;
import pdc.dtos.PersonalDto;
import pdc.dtos.SchichtplangruppeDto;
import pdc.erp.persistence.ErpPersonalRepositoryDouble;
import pdc.model.Abfallcode;
import pdc.model.ErpTransfer;
import pdc.model.Personal;
import pdc.model.Schichtplangruppe;
import pdc.services.ErpMasterFilesService;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ErpMasterFilesControllerIT {
    private static final int TRANSFER_WAIT_MINUTES = 5;
    private static final int MAX_MINUTES_AFTER_TRANSFER = 60;
    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ErpMasterFilesService erpMasterFilesService;
    private Optional<ErpTransfer> lastCompleted;


    @BeforeEach
    void init() {
        long ellapsedMinutes = getEllapsedMinutesFromLastCompletedTransfer();
        if (ellapsedMinutes > MAX_MINUTES_AFTER_TRANSFER) {
            erpMasterFilesService.deleteAllTransfers();
            erpMasterFilesService.transferDataFromErp();
        }
        lastCompleted = erpMasterFilesService.findLastCompletedTransfer();
        assertTrue(lastCompleted.isPresent());
    }

    private long getEllapsedMinutesFromLastCompletedTransfer() {
        Optional<ErpTransfer> lastCompleted = erpMasterFilesService.findLastCompletedTransfer();
        if (lastCompleted.isEmpty()) {
            return MAX_MINUTES_AFTER_TRANSFER + 1;
        } {
            return ChronoUnit.MINUTES.between(lastCompleted.get().getFinishedAt(), LocalDateTime.now());
        }

    }

    @Test
    void listAllfailureCodesFirmaId2() {
        List<AbfallcodeDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/master-files/failure-codes")
                        .queryParam("disableTransferFromErp", true)
                        .build(2))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AbfallcodeDto.class).returnResult().getResponseBody();
        assertThat(list).hasSize(50).extracting(AbfallcodeDto::getFirmaId).containsOnly(2);
    }

    @Test
    void listAllfailureCodesFirmaId5() {
        List<AbfallcodeDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/master-files/failure-codes")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AbfallcodeDto.class).returnResult().getResponseBody();
        assertThat(list).hasSize(34).extracting(AbfallcodeDto::getFirmaId).containsOnly(5);
    }

    @Test
    void listAllEmployees() {
        List<PersonalDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/master-files/employees")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PersonalDto.class).returnResult().getResponseBody();
        assertThat(list).hasSize(ErpPersonalRepositoryDouble.GENERATED_NAMES_COUNT);
        list.forEach(System.out::println);
    }

    @Test
    void listAllWorkgroups() {
        List<SchichtplangruppeDto> list = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/erp/{id}/master-files/work-groups")
                        .queryParam("disableTransferFromErp", true)
                        .build(5))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(SchichtplangruppeDto.class).returnResult().getResponseBody();
        assertThat(list).hasSize(51);
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