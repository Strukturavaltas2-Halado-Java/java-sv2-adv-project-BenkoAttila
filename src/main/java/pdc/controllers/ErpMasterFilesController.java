package pdc.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.*;
import pdc.services.ErpMasterFilesService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "erp master files operations")
@Slf4j
public class ErpMasterFilesController {
    private final ErpMasterFilesService service;

    @GetMapping("/erp/{firmaId}/master-files/failure-codes")
    @Operation(method = "get failure-codes from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<AbfallcodeDto> listAllfailureCodes(@PathVariable int firmaId,
                                                   @RequestParam Optional<Boolean> disableTransferFromErp) {
        if (!disableTransferFromErp.orElse(false)) {
            service.transferDataFromErp();
        }
        return service.listAllActivefailurecodes(firmaId);
    }

    @GetMapping("/erp/{firmaId}/master-files/employees")
    @Operation(method = "get employees from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonalDto> listAllEmployees(@PathVariable int firmaId,
                                              @RequestParam Optional<Boolean> disableTransferFromErp) {
        if (!disableTransferFromErp.orElse(false)) {
            service.transferDataFromErp();
        }
        return service.listAllActiveEmployees(firmaId);
    }

    @GetMapping("/erp/{firmaId}/master-files/work-groups")
    @Operation(method = "get production groups from erp")
    @ResponseStatus(HttpStatus.OK)
    public List<SchichtplangruppeDto> listAllWorkgroups(@PathVariable int firmaId,
                                                        @RequestParam Optional<Boolean> disableTransferFromErp) {
        if (!disableTransferFromErp.orElse(false)) {
            service.transferDataFromErp();
        }
        return service.listAllActiveWorkgroups(firmaId);
    }

    @GetMapping("/erp")
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = "get erp data transfer details list")
    public List<ErpTransferDto> listAllErpTransfers(@RequestParam Optional<Boolean> disableTransferFromErp) {
        log.info(disableTransferFromErp.toString());
        if (!disableTransferFromErp.orElse(false)) {
            service.transferDataFromErp();
        }
        return service.listAllErpTransfers();
    }

    @DeleteMapping("/erp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "vanish erp data transfer list, and initiate a new data transfer")
    public void deleteAllERPTransfers(@RequestParam Optional<Boolean> disableTransferFromErp) {
        service.deleteAllTransfers();
        if (!disableTransferFromErp.orElse(false)) {
//            service.transferDataFromErp();
        }
    }
}
