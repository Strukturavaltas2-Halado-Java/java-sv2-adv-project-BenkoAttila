package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="erppersonals")
@Getter
@Setter
@NoArgsConstructor
public class Personal {
    @Id
    @Column(name="personal_id")
    private int personalId;
    @Column(name="firma_id")
    private int firmaId;
    @Column(name="pers_name")
    private String persName;
    @Column(name="datum_austritt")
    private LocalDate datumAustritt;

    public Personal(int personalId) {
        this.personalId = personalId;
    }
}
