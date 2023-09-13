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
public class ChoirMembership {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne 
    private Choir choir;
    private boolean isApprovedByChoirPresident;
    private boolean isApprovedByChurchElder;
    private boolean isAllowed;
    private Date creationDate=Date.from(Instant.now());
    
}
