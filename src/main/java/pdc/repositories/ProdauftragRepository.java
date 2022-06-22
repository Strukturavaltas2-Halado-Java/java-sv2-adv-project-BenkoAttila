package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdc.model.Prodauftrag;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdauftragRepository extends JpaRepository<Prodauftrag, Long> {
    @Modifying
    @Transactional
    @Query(value="update erpprodauftragen set aktiv = 0", nativeQuery= true)
    void inactivateAll();
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and aktiv=:aktiv")
    List<Prodauftrag> findAllActiveWorkordersByFirmaIdAndAktiv(int firmaId, boolean aktiv);
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and aktiv=:aktiv")
    List<Prodauftrag> findAllByFirmaIdAndProdstufeIdAndAktiv(int firmaId, int prodstufeId, boolean aktiv);


    @Query(value="select p from Prodauftrag p where firmaId=:firmaId and prodstufeId=:prodstufeId and paNrId=:paNrId")
    Optional<Prodauftrag> findByFirmaIdAndProdstufeIdAndPaNrId(int firmaId, int prodstufeId, int paNrId);

    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and pa_nr_id=:paNrId")
    Optional<Prodauftrag> getByFirmaIdAndProdstufeIdAndPaNrId(int firmaId, int prodstufeId, int paNrId);
    @Query(value="select p from Prodauftrag p where aktiv=:aktiv and firma_id=:firmaId and prodstufe_id=:prodstufeId and pa_nr_id=:paNrId")
    Optional<Prodauftrag> getByFirmaIdAndProdstufeIdAndPaNrIdAndAktiv(int firmaId, int prodstufeId, int paNrId, boolean aktiv);
}
