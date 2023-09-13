package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import sda.choir.Model.Choir;
import sda.choir.Model.Event;
import sda.choir.Model.Invitation;
@Repository
public interface InvitationRepository extends JpaRepository<Invitation,Integer>,RevisionRepository<Invitation,Integer,Integer>{

    Page<Invitation> findAllByInvitedChoir(Choir choir, Pageable pages);
    List<Invitation> findAllByInvitedChoir(Choir choir);
    List<Invitation> findAllByEvent(Event event);
    Page<Invitation> findAllByEvent(Event event, Pageable pages);  
      
}
