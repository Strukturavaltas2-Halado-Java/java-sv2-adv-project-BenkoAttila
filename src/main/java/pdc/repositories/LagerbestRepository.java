package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Lagerbestdetail;

import java.util.List;

public interface LagerbestRepository extends JpaRepository<Lagerbestdetail, Long> {
//    @Query(value="select ld from Lagerbestdetail ld left join fetch ld.prodauftrag where stueck_nr=:stueckNr and stueck_teilung=:stueckTeilung")
    List<Lagerbestdetail> findByStueckNrEqualsAndStueckTeilungEquals(int stueckNr, int stueckTeilung);
}
