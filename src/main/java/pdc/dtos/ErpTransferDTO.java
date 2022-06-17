package pdc.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErpTransferDTO {
    private Integer transferId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
