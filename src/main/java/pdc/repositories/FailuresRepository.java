package pdc.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pdc.dtos.FailureDto;
import pdc.model.Failure;
import pdc.model.Prodauftrag;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface FailuresRepository extends JpaRepository<Failure, Long> {
//    @Query(value = "select f from Failure f join fetch prodauftrag p where p.id=:prodauftragId and f.buendel_bc=:buendelBc")
//    List<Failure> findByProdauftragAndBuendelBc(Long prodauftragId, String buendelBc);
//
//    @Query("select f from Failure f where ts_erfassung >=:from and personal_qc=:personalId or personal_qc2=:personalId")
//    List<FailureDto> findByPersonalQCFromDateTime(int personalId, LocalDateTime from);

//    @Query(value = "select f from Failure f join fetch f.prodauftrag pa where prodauftrag_id=:id", nativeQuery = true)
    List<Failure> findByProdauftrag_Id(Long id);

    default List<Failure> findByPersonalQCFromDateTime(int personalId, LocalDateTime from) {
        return findAll();
    }
}
