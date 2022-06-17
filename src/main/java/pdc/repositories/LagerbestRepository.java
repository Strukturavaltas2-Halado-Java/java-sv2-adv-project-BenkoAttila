package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdc.model.Lagerbestdetail;

@Repository
public interface LagerbestRepository extends JpaRepository<Lagerbestdetail, Long> {
}
