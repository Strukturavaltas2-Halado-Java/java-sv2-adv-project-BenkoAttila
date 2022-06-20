package pdc.failures;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.*;
import lombok.RequiredArgsConstructor;
import pdc.model.WorkOrderParams;
import pdc.validators.WorkOrderParamsValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "erp data operations")
public class ErpController {
    private final ErpService service;
    private final WorkOrderParamsValidator workOrderParamsValidator;

    @GetMapping("/erp/{firmaId}/master-files/failure-codes")
    @Operation(method = "get failure-codes from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<AbfallcodeDTO> listAllfailureCodes(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActivefailurecodes(firmaId);
    }

    @GetMapping("/erp/{firmaId}/master-files/employees")
    @Operation(method = "get employees from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonalDTO> listAllEmployees(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveEmployees(firmaId);
    }

    @GetMapping("/erp/{firmaId}/master-files/work-groups")
    @Operation(method = "get production groups from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<SchichtplangruppeDTO> listAllWorkgroups(@PathVariable int firmaId) {
        service.transferDataFromErp();
        return service.listAllActiveWorkgroups(firmaId);
    }

    @GetMapping("/erp/{firmaId}/work-orders")
    @Operation(method = "get work-orders from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDTO> listAllWorkorders(
            @RequestParam Optional<String> stueckNrBc,
            @RequestParam Optional<String> stapelId,
            @RequestParam Optional<String> buendel1,
            @RequestParam Optional<String> buendel2,
            @RequestParam Optional<String> buendel3,
            @PathVariable int firmaId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, 0, 0);
        param.setStueckNrBc(stueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        service.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/erp/{firmaId}/work-orders/{prodstufeId}")
    @Operation(method = "get work-orders from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDTO> listAllWorkorders(@RequestParam Optional<String> StueckNrBc,
                                                  @RequestParam Optional<String> stapelId,
                                                  @RequestParam Optional<String> buendel1,
                                                  @RequestParam Optional<String> buendel2,
                                                  @RequestParam Optional<String> buendel3,
                                                  @PathVariable int firmaId,
                                                  @PathVariable int prodstufeId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, 0);
        param.setStueckNrBc(StueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        service.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/erp/{firmaId}/work-orders/{prodstufeId}/{paNrId}")
    @Operation(method = "get specified work-order")
    @ResponseStatus(HttpStatus.OK)
    public ProdauftragDTO listAllWorkorders(@PathVariable int firmaId,
                                            @PathVariable int prodstufeId,
                                            @PathVariable int paNrId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, paNrId);
        service.transferDataFromErp();
        return service.findWorkorder(param);
    }

    @GetMapping("/erp")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "get erp data transfer details list")
    public List<ErpTransferDTO> listAllErpTransfers() {
        service.transferDataFromErp();
        return service.listAllErpTransfers();
    }

    @DeleteMapping("/erp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "vanish erp data transfer list, and initiate a new data transfer")
    public void deleteAllERPTransfers() {
        service.deleteAllTransfers();
        service.transferDataFromErp();
    }
}
