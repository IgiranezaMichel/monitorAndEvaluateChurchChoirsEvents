package sda.choir.Model;
import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Invitation {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id; 
    @ManyToOne
    private Choir invitedChoir;
    @ManyToOne
    private Event event;
    private String approvalLevel;
    private String approvalTarget;
    private boolean canceled;
    private boolean isAllowed;
}
