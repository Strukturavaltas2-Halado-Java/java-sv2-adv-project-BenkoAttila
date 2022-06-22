package pdc.dtos;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pdc.model.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class FailureDtoTest {
    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    Failure failure;
    LocalDateTime tsErfassung = LocalDateTime.now();

    @BeforeEach
    void init() {
        Prodauftrag prodauftrag = new Prodauftrag(5, 50, 555);
        Personal personal1 = new Personal(101);
        Personal personal2 = new Personal(102);
        Personal personal3 = new Personal(103);
        failure = new Failure(prodauftrag);
        failure.setBuendelBc("BuendelBc");
        failure.setTsErfassung(tsErfassung);
        failure.setPersonalQc(personal1);
        failure.setAbfallcode(new Abfallcode(5, 50, "ac"));
        failure.setSchichtplangruppe(new Schichtplangruppe(1145));
        failure.setMengeAbfall(1.5);
    }

    @Test
    void testMap() {
        SoftAssertions softAssertions = new SoftAssertions();
        FailureDto failureDto = modelMapper.map(failure, FailureDto.class);
        softAssertions.assertThat(failureDto.getFirmaId()).isEqualTo(5);
        softAssertions.assertThat(failureDto.getProdstufeId()).isEqualTo(50);
        softAssertions.assertThat(failureDto.getPaNrId()).isEqualTo(555);
        softAssertions.assertThat(failureDto.getPersonalId()).isEqualTo(0);
        softAssertions.assertThat(failureDto.getBuendelBc()).isEqualTo("BuendelBc");
        softAssertions.assertThat(failureDto.getTsErfassung()).isEqualTo(tsErfassung);
        softAssertions.assertThat(failureDto.getPersonalQc()).isEqualTo(101);
        softAssertions.assertThat(failureDto.getAbfallId()).isEqualTo("ac");
        softAssertions.assertThat(failureDto.getSchichtplangruppeId()).isEqualTo(1145);
        softAssertions.assertThat(failureDto.getMengeAbfall()).isEqualTo(1.5);
        softAssertions.assertAll();
    }
}