package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

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
    private Boolean aktiv;

    public Personal(int personalId) {
        this.personalId = personalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personal personal = (Personal) o;
        return personalId == personal.personalId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId);
    }
}
