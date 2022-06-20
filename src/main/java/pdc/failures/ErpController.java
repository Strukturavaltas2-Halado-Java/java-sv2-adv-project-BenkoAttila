package pdc.failures;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import pdc.model.WorkOrderParams;
import pdc.validators.WorkOrderParamsValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/erp")
@RequiredArgsConstructor
@Validated
public class ErpController {
    private final ErpService service;
    private final ModelMapper modelMapper;
    private final WorkOrderParamsValidator workOrderParamsValidator;

    @GetMapping("/{firmaId}/master-files/failure-codes")
    @ResponseStatus(HttpStatus.OK)
    public List<AbfallcodeDTO> listAllfailureCodes(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActivefailurecodes(firmaId);
    }

    @GetMapping("/{firmaId}/master-files/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonalDTO> listAllEmployees(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveEmployees(firmaId);
    }

    @GetMapping("/{firmaId}/master-files/work-groups")
    @ResponseStatus(HttpStatus.OK)
    public List<SchichtplangruppeDTO> listAllWorkgroups(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveWorkgroups(firmaId);
    }

    @GetMapping("/{firmaId}/work-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDTO> listAllWorkorders(
            @PathVariable int firmaId,
            @RequestParam Optional<String> StueckNrBc,
            @RequestParam Optional<String> stapelId,
            @RequestParam Optional<String> buendel1,
            @RequestParam Optional<String> buendel2,
            @RequestParam Optional<String> buendel3) {
        WorkOrderParams param = new WorkOrderParams(firmaId, 0, 0);
        param.setStueckNrBc(StueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        service.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/{firmaId}/work-orders/{prodstufeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDTO> listAllWorkorders(@PathVariable int firmaId,
                                                  @PathVariable int prodstufeId,
                                                  @RequestParam Optional<String> StueckNrBc,
                                                  @RequestParam Optional<String> stapelId,
                                                  @RequestParam Optional<String> buendel1,
                                                  @RequestParam Optional<String> buendel2,
                                                  @RequestParam Optional<String> buendel3) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, 0);
        param.setStueckNrBc(StueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        service.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/{firmaId}/work-orders/{prodstufeId}/{paNrId}")
    @ResponseStatus(HttpStatus.OK)
    public ProdauftragDTO listAllWorkorders(@PathVariable int firmaId,
                                            @PathVariable int prodstufeId,
                                            @PathVariable int paNrId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, paNrId);
        service.transferDataFromErp();
        return service.findWorkorder(param);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ErpTransferDTO> listAllErpTransfers() {
        service.transferDataFromErp();
        return service.listAllErpTransfers();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllERPTransfers() {
        service.deleteAllTransfers();
        service.transferDataFromErp();
    }
}
