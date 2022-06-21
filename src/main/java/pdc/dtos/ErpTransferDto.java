package pdc.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErpTransferDto {
    private Integer transferId;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
