package pdc.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import pdc.model.AbfallcodeId;

@Data
@NoArgsConstructor
public class FailurecodeDTO {
    private AbfallcodeId id;
    private String fehlerGruppeId;
    private String abfallText;
}
