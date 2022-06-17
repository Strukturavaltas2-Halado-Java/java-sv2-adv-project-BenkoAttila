package pdc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="erptransfers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ErpTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    public ErpTransfer(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public boolean timedOut(int timeout) {
        return startedAt.plusMinutes(timeout).isBefore(LocalDateTime.now());
    }
}
