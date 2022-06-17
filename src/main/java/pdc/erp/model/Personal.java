package pdc.erp.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Personal {
    private int firmaId;
    private int personalId;
    private String persName;
    private LocalDate datumAustritt;
}
