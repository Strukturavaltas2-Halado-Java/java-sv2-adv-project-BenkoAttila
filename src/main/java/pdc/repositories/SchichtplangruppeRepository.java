package pdc.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Schichtplangruppe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

public interface SchichtplangruppeRepository extends JpaRepository<Schichtplangruppe, Integer> {
    @Modifying
    @Transactional
    @Query(value="update erpschichtplangruppen set aktiv = 0", nativeQuery= true)
    void inactivateAll();
    @Query(value = "select s from Schichtplangruppe s where firma_id=:firmaId and aktiv=1")
    Collection<Schichtplangruppe> findAllByFirmaId(int firmaId);
}
