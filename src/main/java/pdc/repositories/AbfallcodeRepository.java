package pdc.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Abfallcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AbfallcodeRepository extends JpaRepository<Abfallcode, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE erpabfallcodes set aktiv = 0", nativeQuery=true)
    public void inactivateAll();

    @Query(value = "select a from Abfallcode a where firma_id=:firmaId and aktiv=:aktiv")
    List<Abfallcode> findByFirmaWithAktivTrue(int firmaId, boolean aktiv);

    @Query(value = "select a from Abfallcode a where firma_id=:firmaId and prodstufe_id=:prodstufeId and abfall_id=:abfallId and aktiv=:aktiv")
    Optional<Abfallcode> findByFirmaIdAndProdstufeIdAndAbfallIdWithAktiv(int firmaId, int prodstufeId, String abfallId, boolean aktiv);

    @Query(value = "select a from Abfallcode a where firma_id=:firmaId and prodstufe_id=:prodstufeId and abfall_id=:abfallId")
    Optional<Abfallcode> findByFirmaIdAndProdstufeIdAndAbfallId(int firmaId, int prodstufeId, String abfallId);
}
