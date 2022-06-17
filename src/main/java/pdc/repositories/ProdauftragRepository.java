package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdc.model.Prodauftrag;
import pdc.model.ProdauftragId;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProdauftragRepository extends JpaRepository<Prodauftrag, ProdauftragId> {
    @Modifying
    @Transactional
    @Query(value="update erpprodauftragen set aktiv = 0", nativeQuery= true)
    void inactivateAll();
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and aktiv=1")
    List<Prodauftrag> listAllActiveWorkorders(int firmaId);
    @Query(value="select p from Prodauftrag p where firma_id=:firmaId and prodstufe_id=:prodstufeId and aktiv=1")
    List<Prodauftrag> listAllActiveWorkorders(int firmaId, int prodstufeId);
}
