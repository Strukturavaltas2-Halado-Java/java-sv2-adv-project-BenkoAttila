package pdc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateFailureCommand {
    private Long id;
    @Min(1)
    private int firmaId;
    @Min(1)
    private int prodstufeId;
    @Min(1)
    private int paNrId;
    private String buendelBc;
    private LocalDateTime tsErfassung;
    @Min(1)
    private int personalQc;
    private int personalQc2;
    private Boolean pruefung2;
    @NotBlank
    private String abfallId;
    private int personalId;
    private int schichtplangruppeId;
    private Double mengeAbfall;
    private Integer stueckNr;
    private Integer stueckTeilung;
    private Double mengeGutz;
    private Boolean zuschnittFertig;
    private Double mengeGutp;
    private Boolean pruefungFertig;
}
