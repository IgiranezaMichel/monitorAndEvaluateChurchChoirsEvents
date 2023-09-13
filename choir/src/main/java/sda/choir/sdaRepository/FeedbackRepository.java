package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer>,RevisionRepository<Feedback,Integer,Integer>{

    List<Feedback> findAllByChoir(Choir choir);

    List<Feedback> findAllByUser(User user);

    List<Feedback> findAllByChurch(Church church);
    
}
