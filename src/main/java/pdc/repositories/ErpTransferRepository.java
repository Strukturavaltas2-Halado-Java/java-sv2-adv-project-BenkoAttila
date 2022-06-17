package pdc.repositories;

import pdc.model.ErpTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Comparator;
import java.util.Optional;

public interface ErpTransferRepository extends JpaRepository<ErpTransfer, Integer> {

    default Optional<ErpTransfer> findCompletedOrderByIdDesc() {
        return findAll().stream().filter(erpTransfer -> erpTransfer.getFinishedAt() != null).sorted(Comparator.comparing(ErpTransfer::getId).reversed()).findFirst();
    }

    default Optional<ErpTransfer> findRunningOrderByIdDesc() {
        return findAll().stream().filter(erpTransfer -> erpTransfer.getFinishedAt() == null).sorted(Comparator.comparing(ErpTransfer::getId).reversed()).findFirst();
    }
}
