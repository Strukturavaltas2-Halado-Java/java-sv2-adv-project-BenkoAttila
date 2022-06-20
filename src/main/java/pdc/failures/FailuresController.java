package pdc.failures;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.FailureDTO;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "failure data collecting operations")
@Slf4j
public class FailuresController {
    private final FailuresService failuresService;

    @GetMapping("/failuresv2")
    @ResponseStatus(HttpStatus.OK)
    public List<FailureDTO> findFailures() {
        return failuresService.findFailures();
    }

    @PostMapping("/failuresv2")
    @Operation(method = "Create new Failure")
    @ResponseStatus(value= HttpStatus.CREATED)
    public String createFailure(@RequestBody CreateFailureCommand command) {
        log.info(command.toString());
        return command.toString();
//        return failuresService.createFailure(command);
    }
}
