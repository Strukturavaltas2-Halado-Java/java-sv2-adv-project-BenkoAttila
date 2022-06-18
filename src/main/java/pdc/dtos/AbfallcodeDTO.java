package pdc.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AbfallcodeDTO {
    private int firmaId;
    private Integer prodstufeId;
    private String abfallId;
    private String fehlerGruppeId;
    private String abfallText;
}
