package pdc.failures;

import org.springframework.web.bind.annotation.*;
import pdc.dtos.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@RestController
@RequestMapping("api/erp")
@RequiredArgsConstructor
public class ErpController {
    private final ErpService service;
    private final ModelMapper modelMapper;
    @GetMapping("/{firmaId}/master-files/failure-codes")
    public List<AbfallCodeDTO> listAllfailureCodes(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActivefailurecodes(firmaId);
    }

    @GetMapping("/{firmaId}/master-files/employees")
    public List<PersonalDTO> listAllEmployees(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveEmployees(firmaId);
    }

    @GetMapping("/{firmaId}/master-files/work-groups")
    public List<SchichtplangruppeDTO> listAllWorkgroups(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveWorkgroups(firmaId);
    }

    @GetMapping("/{firmaId}/work-orders")
    public List<ProdauftragDTO> listAllWorkorders(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveWorkorders(firmaId);
    }
    @GetMapping("/{firmaId}/work-orders/{prodstufeId}")
    public List<ProdauftragDTO> listAllWorkorders(@PathVariable int firmaId, @PathVariable int prodstufeId) {
        service.transferDataFromErp();
        return service.listAllActiveWorkorders(firmaId, prodstufeId);
    }

    @GetMapping
    public List<ErpTransferDTO> listAllErpTransfers() {
        return service.listAllErpTransfers();
    }

    @DeleteMapping
    public void deleteAllERPTransfers() {
        service.deleteAllTransfers();
    }
}
