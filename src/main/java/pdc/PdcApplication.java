package pdc;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pdc.model.Failure;

@SpringBootApplication
public class PdcApplication {
    public static void main(String[] args) {
        SpringApplication.run(PdcApplication.class, args);
    }

    @Bean
    ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
