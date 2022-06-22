package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdc.model.Prodauftragbuendel;

import java.util.List;

@Repository
public interface ProdauftragbuendelRepository extends JpaRepository<Prodauftragbuendel, Long> {
    @Query(value="select p from Prodauftragbuendel p where stapel_id=:stapelId and stueck_nr=:buendel1 and buendelgruppe_id=:buendel2 and stueck_teilung=:buendel3")
    List<Prodauftragbuendel> listaAllByStapelIdAndBuendel1AndBuendel2AndBuendel3(int stapelId, int buendel1, int buendel2, int buendel3);
}
