package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "erpschichtplangruppen")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Schichtplangruppe {
    @Id
    @Column(name= "schichtplangruppe_id")
    private int schichtplangruppeId;
    @Column(name= "firma_id")
    private int firmaId;
    @Column(name= "schichtplangruppe_bez")
    private String schichtplangruppeBez;
    private boolean aktiv = true;

    public Schichtplangruppe(int schichtplangruppeId) {
        this.schichtplangruppeId = schichtplangruppeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schichtplangruppe that = (Schichtplangruppe) o;
        return schichtplangruppeId == that.schichtplangruppeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(schichtplangruppeId);
    }
}
