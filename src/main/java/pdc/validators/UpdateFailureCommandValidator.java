package pdc.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pdc.dtos.CreateFailureCommand;
import pdc.dtos.UpdateFailureCommand;
import pdc.exceptions.PersonalNotFoundException;
import pdc.model.Personal;
import pdc.repositories.PersonalRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateFailureCommandValidator {
    private final PersonalRepository personalRepository;

    public void validateCommand(UpdateFailureCommand command) {
        validatePersonal("personal", command.getPersonalId());
    }

    private void validatePersonal(String attribute, int id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isEmpty()) {
            throw new PersonalNotFoundException(attribute, id);
        }
    }
}
