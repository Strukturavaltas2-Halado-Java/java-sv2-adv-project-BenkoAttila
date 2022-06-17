package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pdc.erp.model.Schichtplangruppe;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ErpSchichtplangruppeRepositoryDoubleTest {
    ErpSchichtplangruppeRepositoryDouble repository = new ErpSchichtplangruppeRepositoryDouble();

    @BeforeEach
    void init() throws IOException {

    }


    @Test
    void testFindAll() throws JsonProcessingException {
        List<Schichtplangruppe> actual = repository.findAll(5)
                .collect(Collectors.toList());
        assertEquals(51, actual.size());
//        ObjectMapper mapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//        System.out.println(mapper.writeValueAsString(actual));
//        actual.forEach(System.out::println);
    }

}