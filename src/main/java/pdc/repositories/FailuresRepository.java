package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import pdc.model.Failure;

import java.time.LocalDateTime;
import java.util.List;

public interface FailuresRepository extends JpaRepository<Failure, Long> {
//    @Query(value = "select f from Failure f join fetch prodauftrag p where p.id=:prodauftragId and f.buendel_bc=:buendelBc")
//    List<Failure> findByProdauftragAndBuendelBc(Long prodauftragId, String buendelBc);
//
//    @Query("select f from Failure f where ts_erfassung >=:from and personal_qc=:personalId or personal_qc2=:personalId")
//    List<FailureDto> findByPersonalQCFromDateTime(int personalId, LocalDateTime from);

//    @Query("select f from Failure f join fetch f.prodauftrag pa where buendel_bc=:buendelBc and pa.id=:id")
    List<Failure> findByProdauftrag_IdAndBuendelBc(Long id, @Nullable String buendelBc);

    @Query("select f from Failure f join fetch f.prodauftrag left join f.personalQc as qc left join f.personalQc2 as qc2 where ts_erfassung >= :from and (qc.personalId=:personalId or qc2.personalId=:personalId)")
    List<Failure> findByPersonalQCFromDateTime(int personalId, LocalDateTime from);

    @Query("select f from Failure f left join fetch f.prodauftrag left join fetch f.abfallcode where f.prodauftrag.id =:id and f.abfallcode.abfallId =:abfallId")
    List<Failure> findByProdauftrag_IdAndAbfallId(Long id, String abfallId);

    @Query("select f from Failure f left join f.personalQc as qc left join f.personalQc2 as qc2 where (qc.personalId=:personalId or qc2.personalId=:personalId)")
    List<Failure> findByPersonalQC(int personalId);

    List<Failure> findByTsErfassungGreaterThan(LocalDateTime from);

    List<Failure> findByProdauftrag_IdEquals(Long id);

    List<Failure> findByProdauftrag_IdEqualsAndStueckNrIsNull(Long id);

    List<Failure> findByProdauftrag_IdEqualsAndStueckNrGreaterThan(Long id, Integer stueckNr);

    List<Failure> findByProdauftrag_IdEqualsAndBuendelBcEquals(Long id, String buendelBc);

    List<Failure> findByProdauftrag_FirmaIdEqualsAndBuendelBcEquals(Integer firmaId, @Nullable String buendelBc);

    List<Failure> findByProdauftrag_FirmaIdEqualsAndProdauftrag_ProdstufeIdEqualsAndBuendelBcEquals(Integer firmaId, Integer prodstufeId, @Nullable String buendelBc);

    List<Failure> findByProdauftrag_FirmaIdEquals(Integer firmaId);

    List<Failure> findByProdauftrag_FirmaIdEqualsAndProdauftrag_ProdstufeIdEquals(Integer firmaId, Integer prodstufeId);


    List<Failure> findByProdauftrag_Id(Long id);
}
