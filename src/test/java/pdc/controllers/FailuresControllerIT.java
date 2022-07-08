package pdc.controllers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import pdc.dtos.*;
import pdc.model.ErpTransfer;
import pdc.services.ErpWorkOrdersService;
import pdc.services.ErpMasterFilesService;
import pdc.model.WorkOrderParams;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from failures"})
@Slf4j
class FailuresControllerIT {
    private static final int TRANSFER_WAIT_MINUTES = 5;
    @Autowired
    WebTestClient webtestClient;

    @Autowired
    ErpMasterFilesService erpMasterFilesService;

    @Autowired
    ErpWorkOrdersService erpWorkOrdersService;

    ProdauftragDto testProdauftrag;
    PersonalDto personalQc;
    PersonalDto personalQc2;
    PersonalDto personal;
    AbfallcodeDto abfallcode1;
    AbfallcodeDto abfallcode2;
    AbfallcodeDto abfallcode3;
    private FailureDto testFailure;

    @BeforeEach
    void init() {
        startTransferIfNeccessary();
        List<ProdauftragDto> list = erpWorkOrdersService.listAllMatchingWorkorders(new WorkOrderParams(5, 50, 0));
        testProdauftrag = list.get(0);
        List<PersonalDto> personals = erpMasterFilesService.listAllActiveEmployees(5);
        personalQc = personals.get(0);
        personal = personals.get(1);
        personalQc2 = personals.get(2);
        List<AbfallcodeDto> failurecodes = erpMasterFilesService.listAllfailureCodes(5);
        abfallcode1 = failurecodes.get(0);
        abfallcode2 = failurecodes.get(1);
        abfallcode3 = failurecodes.get(2);
    }

    private void startTransferIfNeccessary() {
        Optional<ErpTransfer> last = erpMasterFilesService.findLastRunningTransfer();
        Optional<ErpTransfer> lastCompleted = erpMasterFilesService.findLastCompletedTransfer();
        if (lastCompleted.isEmpty()) {
            if (last.isEmpty()) {
                erpMasterFilesService.deleteAllTransfers();
                erpMasterFilesService.transferDataFromErp();
            }
            waitForTransferFinished();
        }
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

    void addTestFailures() {
        CreateFailureCommand command = new CreateFailureCommand();
        command.setFirmaId(testProdauftrag.getFirmaId());
        command.setProdstufeId(testProdauftrag.getProdstufeId());
        command.setPaNrId(testProdauftrag.getPaNrId());
        command.setPersonalQc(personalQc.getPersonalId());
        command.setTsErfassung(LocalDateTime.now().minusHours(8));
        command.setAbfallId(abfallcode1.getAbfallId());
        command.setBuendelBc("bc");
        command.setMengeAbfall(1.0);
        FailureDto actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
        testFailure = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
        command.setStueckNr(123);
        command.setBuendelBc(null);
        command.setPersonalQc(personalQc2.getPersonalId());
        command.setTsErfassung(LocalDateTime.now().minusHours(4));
        command.setPersonalQc2(personalQc.getPersonalId());
        command.setAbfallId(abfallcode2.getAbfallId());
        command.setMengeAbfall(3.0);
        actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
        command.setStueckNr(123);
        command.setAbfallId(abfallcode3.getAbfallId());
        command.setMengeAbfall(4.0);
        actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void findFailuresByFirmaId() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2")
                        .queryParam("firmaId", 5)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(4)
                .extracting(FailureDto::getPaNrId)
                .containsOnly(testProdauftrag.getPaNrId());
    }

    @Test
    void findFailuresByFirmaIdAndBuendelBc() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2")
                        .queryParam("firmaId", 5)
                        .queryParam("buendelBc", "bc")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(2)
                .extracting(FailureDto::getBuendelBc)
                .containsOnly("bc");
    }


    @Test
    void testFindTopFailuresWithAbfallId() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/top")
                        .queryParam("firmaId", testProdauftrag.getFirmaId())
                        .queryParam("prodstufeId", testProdauftrag.getProdstufeId())
                        .queryParam("paNrId", testProdauftrag.getPaNrId())
                        .queryParam("abfallId", abfallcode1.getAbfallId())
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(1);
        assertEquals(abfallcode1.getAbfallId(), list.get(0).getAbfallId());
        assertEquals(2.0, list.get(0).getMengeAbfall());
    }

    @Test
    void testFindTopFailuresWithoutStueckNr() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/top")
                        .queryParam("firmaId", testProdauftrag.getFirmaId())
                        .queryParam("prodstufeId", testProdauftrag.getProdstufeId())
                        .queryParam("paNrId", testProdauftrag.getPaNrId())
                        .queryParam("withStueckNr", false)
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(1);
        list.forEach(failureDto -> log.info(failureDto.toString()));
        assertEquals(abfallcode1.getAbfallId(), list.get(0).getAbfallId());
        assertEquals(2.0, list.get(0).getMengeAbfall());
    }

    @Test
    void testFindTopFailuresWithStueckNr() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/top")
                        .queryParam("firmaId", testProdauftrag.getFirmaId())
                        .queryParam("prodstufeId", testProdauftrag.getProdstufeId())
                        .queryParam("paNrId", testProdauftrag.getPaNrId())
                        .queryParam("withStueckNr", true)
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(2);
        list.forEach(failureDto -> log.info(failureDto.toString()));
        assertEquals(abfallcode3.getAbfallId(), list.get(0).getAbfallId());
        assertEquals(4.0, list.get(0).getMengeAbfall());
    }

    @Test
    void findFailureByPersonalLast12Hours() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/")
                        .queryParam("personalId", personalQc.getPersonalId())
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(3);
        assertEquals(2.0, list.stream()
                .filter(failureDto -> failureDto.getAbfallId().equals(abfallcode1.getAbfallId()))
                .mapToDouble(FailureDto::getMengeAbfall)
                .sum());
    }

    @Test
    void findFailureByPersonalLast6Hours() {
        addTestFailures();
        List<FailureDto> list = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/")
                        .queryParam("personalId", personalQc.getPersonalId())
                        .queryParam("hours", 6)
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertThat(list).hasSize(2);
        assertEquals(7.0, list.stream()
                .mapToDouble(FailureDto::getMengeAbfall)
                .sum());
    }



    @Test
    void findFailure() {
        addTestFailures();
        FailureDto actual = webtestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/{id}")
                        .build(testFailure.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertEquals(testFailure.getId(), actual.getId());
        assertEquals(testFailure.getFirmaId(), actual.getFirmaId());
        assertEquals(testFailure.getPaNrId(), actual.getPaNrId());
        assertEquals(testFailure.getMengeAbfall(), actual.getMengeAbfall());
        assertEquals(testFailure.getAbfallId(), actual.getAbfallId());
    }

    @Test
    void createFailureWithWrongPA() {
        CreateFailureCommand command = new CreateFailureCommand();
        command.setFirmaId(testProdauftrag.getFirmaId());
        command.setProdstufeId(testProdauftrag.getProdstufeId());
        command.setPaNrId(99999999);
        command.setPersonalQc(personalQc.getPersonalId());
        command.setAbfallId(abfallcode1.getAbfallId());

        webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createFailureWithWrongPersonalQc() {
        CreateFailureCommand command = new CreateFailureCommand();
        command.setFirmaId(testProdauftrag.getFirmaId());
        command.setProdstufeId(testProdauftrag.getProdstufeId());
        command.setPaNrId(testProdauftrag.getPaNrId());
        command.setPersonalQc(99999);
        command.setAbfallId(abfallcode1.getAbfallId());

        FailureDto actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void createFailureWithEmptyPersonalQc() {
        CreateFailureCommand command = new CreateFailureCommand();
        command.setFirmaId(testProdauftrag.getFirmaId());
        command.setProdstufeId(testProdauftrag.getProdstufeId());
        command.setPaNrId(testProdauftrag.getPaNrId());
        command.setAbfallId(abfallcode1.getAbfallId());

        FailureDto actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void createFailureWithEmptyStueckNrButWithStuckTeilung() {
        CreateFailureCommand command = new CreateFailureCommand();
        command.setFirmaId(testProdauftrag.getFirmaId());
        command.setProdstufeId(testProdauftrag.getProdstufeId());
        command.setPaNrId(testProdauftrag.getPaNrId());
        command.setAbfallId(abfallcode1.getAbfallId());
        command.setStueckTeilung(11);

        FailureDto actual = webtestClient.post()
                .uri("/api/failuresv2")
                .bodyValue(command)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void updateFailureWithPersonal() {
        addTestFailures();
        UpdateFailureCommand command = new UpdateFailureCommand(personal.getPersonalId());
        FailureDto actual = webtestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/failuresv2/{id}")
                        .build(testFailure.getId()))
                .bodyValue(command)
                .exchange()
                .expectStatus().isOk()
                .expectBody(FailureDto.class)
                .returnResult()
                .getResponseBody();
        assertEquals(command.getPersonalId(), actual.getPersonalId());
    }
}