package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pdc.model.Failure;

import java.util.List;

public interface FailuresRepository extends JpaRepository<Failure, Long> {

}
