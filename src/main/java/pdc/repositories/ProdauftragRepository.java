package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdc.model.Prodauftrag;
import pdc.model.ProdauftragId;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdauftragRepository extends JpaRepository<Prodauftrag, Long> {
    @Modifying
    @Transactional
    @Query(value="update erpprodauftragen set aktiv = 0", nativeQuery= true)
    void inactivateAll();
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and aktiv=1")
    List<Prodauftrag> listAllActiveWorkorders(int firmaId);
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and aktiv=1")
    List<Prodauftrag> listAllActiveWorkorders(int firmaId, int prodstufeId);

    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and pa_nr_id=:paNrId")
    Optional<Prodauftrag> findByFirmaIdAndProdstufeIdAndPaNrId(int firmaId, int prodstufeId, int paNrId);

    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and pa_nr_id=:paNrId and aktiv=1")
    Optional<Prodauftrag> findByFirmaIdAndProdstufeIdAndPaNrIdWithAktivTrue(int firmaId, int prodstufeId, int paNrId);

}
