package pdc.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.*;
import lombok.RequiredArgsConstructor;
import pdc.services.ErpMasterFilesService;
import pdc.services.ErpWorkOrdersService;
import pdc.model.WorkOrderParams;
import pdc.validators.WorkOrderParamsValidator;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "erp work order related operations")
public class ErpWorkOrdersController {
    private final ErpWorkOrdersService service;
    private final ErpMasterFilesService masterFilesService;
    private final WorkOrderParamsValidator workOrderParamsValidator;
    @GetMapping("/erp/{firmaId}/work-orders")
    @Operation(method = "get work-orders from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDto> listAllWorkorders(
            @RequestParam Optional<String> stueckNrBc,
            @RequestParam Optional<String> stapelId,
            @RequestParam Optional<String> buendel1,
            @RequestParam Optional<String> buendel2,
            @RequestParam Optional<String> buendel3,
            @PathVariable int firmaId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, 0, 0);
        param.setStueckNrBc(stueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        new WorkOrderParamsValidator().validate(param);
        masterFilesService.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/erp/{firmaId}/work-orders/{prodstufeId}")
    @Operation(method = "get work-orders from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdauftragDto> listAllWorkorders(@RequestParam Optional<String> StueckNrBc,
                                                  @RequestParam Optional<String> stapelId,
                                                  @RequestParam Optional<String> buendel1,
                                                  @RequestParam Optional<String> buendel2,
                                                  @RequestParam Optional<String> buendel3,
                                                  @PathVariable int firmaId,
                                                  @PathVariable int prodstufeId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, 0);
        new WorkOrderParamsValidator().validate(param);
        param.setStueckNrBc(StueckNrBc);
        param.setStapelBuendel(stapelId, buendel1, buendel2, buendel3);
        masterFilesService.transferDataFromErp();
        return service.listAllMatchingWorkorders(param);
    }

    @GetMapping("/erp/{firmaId}/work-orders/{prodstufeId}/{paNrId}")
    @Operation(method = "get specified work-order")
    @ResponseStatus(HttpStatus.OK)
    public ProdauftragDto listAllWorkorders(@PathVariable int firmaId,
                                            @PathVariable int prodstufeId,
                                            @PathVariable int paNrId) {
        WorkOrderParams param = new WorkOrderParams(firmaId, prodstufeId, paNrId);
        new WorkOrderParamsValidator().validate(param);
        masterFilesService.transferDataFromErp();
        return service.findWorkorder(param);
    }
}
