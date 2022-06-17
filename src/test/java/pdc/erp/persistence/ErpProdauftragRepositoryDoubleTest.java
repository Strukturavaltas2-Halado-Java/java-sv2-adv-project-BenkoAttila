package pdc.erp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import pdc.erp.model.Prodauftrag;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErpProdauftragRepositoryDoubleTest {


    ErpProdauftragRepositoryDouble repository = new ErpProdauftragRepositoryDouble();

    @Test
    void testFindAll() throws JsonProcessingException {
        List<Prodauftrag> actual = repository.findAll()
                .collect(Collectors.toList());
        assertEquals(888, actual.size());
//        ObjectMapper mapper = JsonMapper.builder()
//                .addModule(new JavaTimeModule())
//                .build();
//        System.out.println(mapper.writeValueAsString(actual));
    }

}