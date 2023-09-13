package sda.choir.Model;
import java.sql.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Contribution {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private double amount;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Choir choir;
    @ManyToOne
    private Church church;
    @OneToMany(mappedBy = "contribution")
    private List<OfferedContribution>offeredList;
}
