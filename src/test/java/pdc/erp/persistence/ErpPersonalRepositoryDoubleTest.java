package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pdc.erp.model.Personal;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErpPersonalRepositoryDoubleTest {
    ErpPersonalRepositoryDouble repository;

    @BeforeEach
    void init() throws IOException {
        repository =  new ErpPersonalRepositoryDouble();
    }


    @Test
    void testFindAll() throws JsonProcessingException {
        List<Personal> actual = repository.findAll(5)
                .collect(Collectors.toList());
        assertEquals(ErpPersonalRepositoryDouble.GENERATED_NAMES_COUNT, actual.size());
    }

}