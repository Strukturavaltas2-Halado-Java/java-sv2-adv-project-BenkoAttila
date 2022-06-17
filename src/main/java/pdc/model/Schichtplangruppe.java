package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "erpschichtplangruppen")
@Getter
@Setter
@NoArgsConstructor
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

}
