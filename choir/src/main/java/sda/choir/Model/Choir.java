package sda.choir.Model;
import java.sql.Date;
import java.util.List;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Audited
public class Choir {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;  
    private String name;
    @Column(columnDefinition = "text")
    private String history;
    @Column(columnDefinition = "longBlob")
    private String logo;
    @JsonIgnore
    @ManyToOne
    private Church church;
    @JsonIgnore
    @ManyToOne
    private User leader;
    private Date startDate;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<User> users;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<Announcement> announcementList;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<ChoirMembership>membershipList;
    @JsonIgnore
    @OneToMany(mappedBy="invitedChoir")
    private List<Invitation>invitationList;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<Event>eventList;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<Contribution>contributionList;
    @JsonIgnore
    @OneToMany(mappedBy="choir")
    private List<Feedback>feedbackList;
}
