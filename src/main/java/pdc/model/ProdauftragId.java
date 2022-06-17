package pdc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdauftragId implements Serializable {
    @Column(name = "firma_id", nullable = false, insertable = false, updatable = false)
    private Integer firmaId;
    @Column(name = "prodstufe_id", nullable = false, insertable = false, updatable = false)
    private Integer prodstufeId;
    @Column(name = "pa_nr_id", nullable = false, insertable = false, updatable = false)
    private Integer paNrId;
}