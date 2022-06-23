package pdc;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import pdc.dtos.CustomMapper;
import pdc.dtos.FailureDto;
import pdc.model.Failure;
import pdc.model.Personal;
import pdc.model.Schichtplangruppe;

@SpringBootApplication
public class PdcApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdcApplication.class, args);
    }

    @Bean
    ModelMapper createModelMapper() {
        return new ModelMapper();
    }
}
