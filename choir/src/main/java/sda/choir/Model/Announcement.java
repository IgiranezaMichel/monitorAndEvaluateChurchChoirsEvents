package sda.choir.Model;

import java.sql.Date;
import java.time.Instant;

import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Announcement {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private String referenceLink;
    private java.util.Date creationDate=java.util.Date.from(Instant.now());
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Choir choir;
    @ManyToOne
    private Church church;
}
