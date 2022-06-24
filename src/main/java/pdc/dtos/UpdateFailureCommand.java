package pdc.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateFailureCommand {
    @Min(1)
    private int personalId;

    public UpdateFailureCommand(int personalId) {
        this.personalId = personalId;
    }
}
