package pdc.failures;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pdc.repositories.ErpTransferRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ErpService {
    private static final int WAIT_BEFORE_NEW_COPY = 90;
    private static final int COPY_TIMEOUT = 5;
    private final ModelMapper modelMapper;
    private final ErpTransferRepository erpTransferRepository;


    void transferDataFromErp() {
        List<pdc.model.ErpTransfer> list = erpTransferRepository.findAll();
        Integer currentTransferId = null;
        if (shouldStartTransfer()) {
            pdc.model.ErpTransfer saved = erpTransferRepository.save(new pdc.model.ErpTransfer(LocalDateTime.now()));
            currentTransferId = saved.getId();
            System.out.println(currentTransferId);
        }
        if (currentTransferId != null) {
            saveErpData(currentTransferId);
        }
    }

    public boolean shouldStartTransfer() {
        Optional<pdc.model.ErpTransfer> lastRunning = erpTransferRepository.findRunningOrderByIdDesc();
        Optional<pdc.model.ErpTransfer> lastCompleted = erpTransferRepository.findCompletedOrderByIdDesc();
        if ((erpTransferRepository.findAll().isEmpty() || !lastCompleted.isPresent()) && (!lastRunning.isPresent() || lastRunning.get().timedOut(COPY_TIMEOUT))) {
            return true;
        }
        if (lastCompleted.isPresent() && lastCompleted.get().timedOut(WAIT_BEFORE_NEW_COPY) && (!lastRunning.isPresent() || lastRunning.get().timedOut(COPY_TIMEOUT))) {
            return true;
        }
        return false;
    }

    public Optional<pdc.model.ErpTransfer> findLastCompletedTransfer() {
        return erpTransferRepository.findCompletedOrderByIdDesc();
    }

    public Optional<pdc.model.ErpTransfer> findLastRunningTransfer() {
        return erpTransferRepository.findRunningOrderByIdDesc();
    }

    @Transactional
    public void deleteAllTransfers() {
        cleanErpData();
        erpTransferRepository.deleteAll();
    }

    private void cleanErpData() {
    }

    @Transactional
    public pdc.model.ErpTransfer createTransfer(LocalDateTime startedAt) {
        pdc.model.ErpTransfer erpTransfer = new pdc.model.ErpTransfer(startedAt);
        erpTransferRepository.save(erpTransfer);
        return erpTransfer;
    }

    @Transactional
    public pdc.model.ErpTransfer closeTransferById(int id) {
        pdc.model.ErpTransfer erpTransfer = erpTransferRepository.findById(id).get();
        erpTransfer.setFinishedAt(LocalDateTime.now());
        erpTransferRepository.save(erpTransfer);
        return erpTransfer;
    }


    @Transactional
    private void saveErpData(Integer currentTransferId) {
        if (currentTransferId != null) {
            pdc.model.ErpTransfer actual = erpTransferRepository.findById(currentTransferId).get();
            actual.setFinishedAt(LocalDateTime.now());
            erpTransferRepository.save(actual);
        }
    }
}
