package pdc.failures;

import org.springframework.data.jpa.repository.JpaRepository;
import pdc.model.Failure;

public interface failureRepository extends JpaRepository<Failure, Long> {
}
