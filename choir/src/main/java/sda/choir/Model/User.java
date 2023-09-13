package sda.choir.Model;
import java.util.List;

import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class User {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private String gender;
    private String phoneNumber;
    @Column(unique =true)
    private String email;
    private String role;
    @Column(unique =true,nullable = false)
    private String username;
    private String password;
    @ManyToOne
    private Choir choir;
    @ManyToOne
    private Church church;
    @OneToMany(mappedBy = "leader")
    private List<Choir> choirLeader;
    @OneToMany(mappedBy = "user")
    private List<ChoirMembership>membershipList;
    @OneToMany(mappedBy = "user")
    private List<OfferedContribution>listOfContributions;
    @OneToMany(mappedBy = "user")
    private List<Feedback>feedbackList;
}
