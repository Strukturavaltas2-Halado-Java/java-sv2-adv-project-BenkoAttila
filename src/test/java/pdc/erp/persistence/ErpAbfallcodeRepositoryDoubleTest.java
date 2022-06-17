package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import pdc.erp.model.Abfallcode;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ErpAbfallcodeRepositoryDoubleTest {

    ErpAbfallcodeRepositoryDouble repository = new ErpAbfallcodeRepositoryDouble();



    @Test
    void testFindAll() throws JsonProcessingException {
        List<Abfallcode> actual = repository.findAll()
                .collect(Collectors.toList());
        assertEquals(84, actual.size());
    }

}