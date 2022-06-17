package pdc.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Abfallcode;
import pdc.model.AbfallcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface AbfallcodeRepository extends JpaRepository<Abfallcode, AbfallcodeId> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE erpabfallcodes set aktiv = 0", nativeQuery=true)
    public void inactivateAll();

    @Query(value = "select a from Abfallcode a where firma_id=:firmaId and aktiv=1")
    List<Abfallcode> findByFirmaWithAktivTrue(int firmaId);
}
