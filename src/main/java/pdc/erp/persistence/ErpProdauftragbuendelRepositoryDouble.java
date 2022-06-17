package pdc.erp.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pdc.erp.model.Prodauftragbuendel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class ErpProdauftragbuendelRepositoryDouble {

    public Stream<Prodauftragbuendel> findAll() {
        try {
            return readFromJson().stream();
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file!", ioe);
        }
    }

    private List<Prodauftragbuendel> readFromJson() throws IOException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        return mapper.readValue(new File("src/main/resources/erp/json/prodauftragbuendel.json"), new TypeReference<List<Prodauftragbuendel>>(){});
    }
}
