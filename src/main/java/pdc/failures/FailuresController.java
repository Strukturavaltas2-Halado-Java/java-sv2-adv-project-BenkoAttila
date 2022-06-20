package pdc.failures;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.FailureDTO;

import java.util.List;

@RestController
@RequestMapping("api/failures-v2")
@RequiredArgsConstructor
public class FailuresController {
//    private final FailuresService failuresService;
//
//    @GetMapping
//    List<FailureDTO> findFailures() {
//        return failuresService.findFailures();
//    }
//
//    @PostMapping
//    FailureDTO createFailure(@RequestBody CreateFailureCommand command) {
//        return failuresService.createFailure(command);
//    }
}
