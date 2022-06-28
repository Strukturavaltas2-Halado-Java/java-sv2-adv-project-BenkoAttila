package pdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pdc.model.Personal;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

public interface PersonalRepository extends JpaRepository<Personal, Integer> {
    @Modifying
    @Transactional
    @Query(value="update erppersonals set aktiv=false", nativeQuery= true)
    void inactivateAll();
//
//    @Query("select p from Personal p where aktiv=1 and firma_id=:firmaId")
//    List<Personal> findAllByFirmaId(int firmaId);

    List<Personal> findByFirmaIdEqualsAndAktivIsTrue(int firmaId);


}
