package pdc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "erpabfallcodes")
@Data
@NoArgsConstructor
public class Abfallcode {
    @Id
    @EmbeddedId
    private AbfallcodeId id;
    private boolean aktiv = true;
    @Column(name = "fehler_gruppe_id")
    private String fehlerGruppeId;
    @Column(name = "abfall_text")
    private String abfallText;
}
