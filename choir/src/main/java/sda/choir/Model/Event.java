package sda.choir.Model;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Event {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private String participant;
    @ManyToOne
    private Choir choir;
    @OneToMany(mappedBy = "event")
    private List<Invitation> invitationList;
    private Date startDate;
    private Date endDate;
    private String address;
    private java.util.Date createDate=java.util.Date.from(Instant.now());
}
