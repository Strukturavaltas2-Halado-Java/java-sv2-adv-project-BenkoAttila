package pdc.erp.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pdc.erp.model.Personal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class ErpPersonalRepositoryDouble {
    public static final int GENERATED_NAMES_COUNT = 250;
    private List<String> vezeteknevek;
    private List<String> keresztnevek;
    private Random random = new Random();

    public Stream<Personal> findAll(int firmaId) {
        List<String> names = null;
        try {
            names = Files.readAllLines(Paths.get("src/main/resources/erp/json/names.txt"));
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read names", ioe);
        }
        names = names.stream().map(s -> s.replace("id.", "")
                        .replace("ifj.", "")
                        .replace("PhD", "")
                        .replace("Prof.", "")
                        .replace("Dr.", "")
                        .trim())
                .toList();
        vezeteknevek = names.stream().map(s -> {
            String[] parts = s.split(" ");
            return parts[0];
        }).toList();
        keresztnevek = names.stream().map(s -> {
            String[] parts = s.split(" ");
            return parts[1];
        }).toList();
        return Stream.iterate(100,integer -> integer+1 )
        .limit(GENERATED_NAMES_COUNT)
                .map(i-> generatePersonal(firmaId, i));
    }

    private Personal generatePersonal(int firmaId, int i) {
        return new Personal(firmaId, i, generaterandomName(), true);
    }
    private String generaterandomName() {
        int i = random.nextInt(vezeteknevek.size());
        int j = random.nextInt(keresztnevek.size());
        return vezeteknevek.get(i) + " " + keresztnevek.get(j);
    }

}
