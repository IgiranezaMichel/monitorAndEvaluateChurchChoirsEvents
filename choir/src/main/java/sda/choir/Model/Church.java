package sda.choir.Model;
import java.util.List;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Audited
public class Church {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private String location;
    private String type;
    
    @ManyToOne
    @JsonIgnore
    private Church churchId;
    @JsonIgnore
    @OneToMany(mappedBy = "churchId")
    private List<Church> churchList;
    @JsonIgnore
    @OneToMany(mappedBy = "church")
    private List<User>userList;
    @JsonIgnore
    @OneToMany(mappedBy = "church")
    private List<Announcement>announcementList;
    @JsonIgnore
    @OneToMany(mappedBy="church")
    private List<Contribution>contributionList;
    @JsonIgnore
    @OneToMany(mappedBy="church")
    private List<Choir>choirList;
    @JsonIgnore
    @OneToMany(mappedBy="church")
    private List<Feedback>feedbackList;
}
