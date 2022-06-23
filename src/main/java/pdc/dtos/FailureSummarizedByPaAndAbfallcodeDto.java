package pdc.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FailureSummarizedByPaAndAbfallcodeDto {
    private int firmaId;
    private int prodstufeId;
    private int paNrId;
    private String abfallId;
    private Double mengeAbfall;

    public void addMengeAbfall(Double mengeAbfall) {
        this.mengeAbfall += mengeAbfall;
    }

    public long getId() {
        return 0L;
    }

    public String getBuendelBc() {
        return "";
    }

    public LocalDateTime getTsErfassung() {
        return null;
    }

    public int getPersonalQc() {
        return 0;
    }

    public int getPersonalQc2() {
        return 0;
    }

    public Boolean getPruefung2() {
        return false;
    }

    public int getPersonalId() {
        return 0;
    }

    public int getSchichtplangruppeId() {
        return 0;
    }

    public Integer getStueckNr() {
        return 0;
    }

    public Integer getStueckTeilung() {
        return 0;
    }

    public Double getMengeGutz() {
        return 0.0;
    }

    public Boolean getZuschnittFertig() {
        return false;
    }

    public Double getMengeGutp() {
        return 0.0;
    }

    public Boolean getPruefungFertig() {
        return false;
    }
}
