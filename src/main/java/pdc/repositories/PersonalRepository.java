package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdc.model.Personal;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {
    @Modifying
    @Transactional
    @Query(value="update erppersonals set datum_austritt = '2000-01-01'", nativeQuery= true)
    void inactivateAll();

    @Query(value = "select p from Personal p where firma_id=:firmaId and datumAustritt is NULL")
    List<Personal> listActiveEmployees(int firmaId);
}
