package sda.choir.Model;
import java.time.Instant;
import java.util.Date;

import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class OfferedContribution {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Contribution contribution;
    @ManyToOne
    private User user;
    private double amount;
    private String paymentCode;
    private Date createDate=Date.from(Instant.now());
}
