package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import pdc.erp.model.Lagerbestdetail;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErpLagerbestRepositoryDoubleTest {

    ErpLagerbestRepositoryDouble repository = new ErpLagerbestRepositoryDouble();


    @Test
    void testFindAllDetail() throws JsonProcessingException {
        List<Lagerbestdetail> actual = repository.findAllDetail()
                .collect(Collectors.toList());
        assertEquals(406, actual.size());
    }

}