package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import pdc.erp.model.Prodauftragbuendel;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErpProdauftragbuendelRepositoryDoubleTest {


    ErpProdauftragbuendelRepositoryDouble repository = new ErpProdauftragbuendelRepositoryDouble();

    @Test
    void testFindAll() throws JsonProcessingException {
        List<Prodauftragbuendel> actual = repository.findAll()
                .collect(Collectors.toList());
        assertEquals(4287, actual.size());
//        ObjectMapper mapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//        System.out.println(mapper.writeValueAsString(actual));
    }

}