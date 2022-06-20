package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Lagerbestdetail;

import java.util.List;

public interface LagerbestRepository extends JpaRepository<Lagerbestdetail, Long> {
    @Query(value="select ld from Lagerbestdetail ld join fetch ld.prodauftrag where stueck_nr=:stueckNr and stueck_teilung=:stueckTeilung")
    List<Lagerbestdetail> findByStueckNrAndStueckTeilung(int stueckNr, int stueckTeilung);
}
