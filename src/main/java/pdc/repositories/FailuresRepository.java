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

    @Query("select f from Failure f join fetch f.prodauftrag pa where buendel_bc=:buendelBc and pa.id=:id")
    List<Failure> findByBuendelBcAndProdauftrag_Id(String buendelBc, Long id);

    @Query("select f from Failure f join fetch f.prodauftrag left join f.personalQc as qc left join f.personalQc2 as qc2 where ts_erfassung >= :from and (qc.personalId=:personalId or qc2.personalId=:personalId)")
    List<Failure> findByPersonalQCFromDateTime(int personalId, LocalDateTime from);

    @Query("select f from Failure f left join fetch f.prodauftrag left join fetch f.abfallcode where f.prodauftrag.id =:id and f.abfallcode.abfallId =:abfallId")
    List<Failure> findByProdauftrag_IdAndAbfallId(Long id, String abfallId);

    @Query("select f from Failure f left join f.personalQc as qc left join f.personalQc2 as qc2 where (qc.personalId=:personalId or qc2.personalId=:personalId)")
    List<Failure> findByPersonalQC(int personalId);

    List<Failure> findByTsErfassungGreaterThan(LocalDateTime from);

    List<Failure> findByProdauftrag_IdEquals(Long id);
}
