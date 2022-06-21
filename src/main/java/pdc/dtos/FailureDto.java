package pdc.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.spi.DestinationSetter;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FailureDto {
    private Long id;
    private int firmaId;
    private int prodstufeId;
    private int paNrId;
    private String buendelBc;
    private LocalDateTime tsErfassung;
    private int personalQc;
    private int personalQc2;
    private Boolean pruefung2;
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
