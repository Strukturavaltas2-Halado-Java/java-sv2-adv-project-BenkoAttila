package pdc.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SummaryByWorkOrderAndFailureCodeKey {
    private Integer firmaId;
    private Integer prodstufeId;
    private Integer paNrId;
    private String abfallId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryByWorkOrderAndFailureCodeKey that = (SummaryByWorkOrderAndFailureCodeKey) o;
        return Objects.equals(firmaId, that.firmaId) && Objects.equals(prodstufeId, that.prodstufeId) && Objects.equals(paNrId, that.paNrId) && Objects.equals(abfallId, that.abfallId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firmaId, prodstufeId, paNrId, abfallId);
    }
}
