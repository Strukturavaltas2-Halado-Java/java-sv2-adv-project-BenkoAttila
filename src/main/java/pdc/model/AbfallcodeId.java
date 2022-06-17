package pdc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AbfallcodeId implements Serializable {
    @Column(name = "firma_id", nullable = false)
    private Integer firmaId;
    @Column(name = "prodstufe_id", nullable = false)
    private Integer prodstufeId;
    @Column(name = "abfall_id", nullable = false)
    private String abfallId;
}
