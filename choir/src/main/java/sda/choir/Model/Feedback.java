package sda.choir.Model;
import java.text.SimpleDateFormat;
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
public class Feedback {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Choir choir;
    @ManyToOne
    private Church church;
    private String email;
    private String message;
    private Date createDate=Date.from(Instant.now());
    public String getCreationDate(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd,MMMM,yyyy HH:mm");
        return sdf.format(createDate);
    }
}
