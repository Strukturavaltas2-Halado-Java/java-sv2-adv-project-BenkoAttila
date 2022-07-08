package pdc.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchichtplangruppeDto {
    private int schichtplangruppeId;
    private String schichtplangruppeBez;
}
