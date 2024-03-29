package pdc.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.FailureDto;
import pdc.dtos.UpdateFailureCommand;
import pdc.model.FailuresParams;
import pdc.services.FailuresService;
import pdc.validators.CreateFailureCommandValidator;
import pdc.validators.FailuresParamsValidator;
import pdc.validators.UpdateFailureCommandValidator;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "failure data collecting operations")
@Slf4j
public class FailuresController {
    private final FailuresService failuresService;

    private final CreateFailureCommandValidator createFailureCommandValidator;

    private final UpdateFailureCommandValidator updateFailureCommandValidator;

    @GetMapping("/failuresv2")
    @Operation(method = "Eltárolt hibák lekérdezése")
    @ResponseStatus(HttpStatus.OK)
    public List<FailureDto> findFailures(@RequestParam Optional<Integer> firmaId,
                                         @RequestParam Optional<Integer> prodstufeId,
                                         @RequestParam Optional<Integer> paNrId,
                                         @RequestParam Optional<String> buendelBc,
                                         @RequestParam Optional<String> abfallId,
                                         @RequestParam Optional<String> hours,
                                         @RequestParam Optional<String> personalId) {
        FailuresParams params = new FailuresParams(firmaId, prodstufeId, paNrId);
        params.setPersonalParams(hours, personalId);
        params.setBuendelBc(buendelBc);
        log.info("findFailures " + params.toString());
        new FailuresParamsValidator().validate(params);
        return failuresService.findFailures(params);
    }

    @GetMapping("/failuresv2/top")
    @Operation(method = "Kiválasztott PA-hoz rögzített top hibák lekérdezése hibakód vagy mennyisége sorrend alapján")
    @ResponseStatus(HttpStatus.OK)
    public List<FailureDto> findFailures(@RequestParam Optional<Integer> firmaId,
                                         @RequestParam Optional<Integer> prodstufeId,
                                         @RequestParam Optional<Integer> paNrId,
                                         @RequestParam Optional<String> abfallId,
                                         @RequestParam Optional<String> withStueckNr,
                                         @RequestParam Optional<String> count) {
        log.info(paNrId.toString());
        FailuresParams params = new FailuresParams(firmaId, prodstufeId, paNrId);
        params.setTopParams(abfallId, withStueckNr, count);
        log.info("findFailures/top " + params.toString());
        new FailuresParamsValidator().validate(params);
        return failuresService.findTopFailures(params);
    }


    @GetMapping("/failuresv2/{id}")
    @Operation(method = "get specified failure entity")
    public FailureDto findFailure(@PathVariable int id) {
        return failuresService.findFailure(id);
    }

    @PostMapping("/failuresv2")
    @Operation(method = "Create new Failure")
    @ResponseStatus(value= HttpStatus.CREATED)
    public FailureDto createFailure(@RequestBody @Valid CreateFailureCommand command) {
        createFailureCommandValidator.validateCommand(command);
        return failuresService.createFailure(command);
    }

    @PutMapping("/failuresv2/{id}")
    @Operation(method = "Update failure with personal")
    @ResponseStatus(HttpStatus.OK)
    public FailureDto updateFailureWithPersonal(@PathVariable long id, @RequestBody @Valid UpdateFailureCommand command) {
        updateFailureCommandValidator.validateCommand(command);
        return failuresService.updateFailure(id, command);
    }
}
