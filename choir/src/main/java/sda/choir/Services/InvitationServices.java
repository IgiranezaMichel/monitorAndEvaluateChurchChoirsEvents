package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.Invitation;
import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.Event;
import sda.choir.sdaRepository.InvitationRepository;
@Service
public class InvitationServices {
    @Autowired private InvitationRepository invitationRepository;
public Invitation saveOrUpdateInvitation(Invitation data)
{
    return invitationRepository.save(data);
}
// delete Invitation
public String deleteInvitation(int id)
{
    try {
        Invitation Invitation=this.findById(id);
        if(Invitation!=null)
         {
            invitationRepository.deleteById(id);
            return Invitation.getInvitedChoir().getName()+" has deleted successfully";
         }
         else
         return "No such Invitation found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Invitation page
public Page<Invitation>findAllInvitationpage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return invitationRepository.findAll(pages);}
// find All Invitation page
public List<Invitation>findListofInvitation()
{return invitationRepository.findAll();}

// get Invitation by id
public Invitation findById(int id)
{  return invitationRepository.findById(id).orElse(null);}

// find Pageable Invitation by choir
public Page<Invitation> findAllInvitationpageByInvitedChoir(Choir choir,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return invitationRepository.findAllByInvitedChoir(choir,pages);
}
// get list of choir
public List<Invitation> findListofInvitationByTitle(Choir choir,int page,int size,String sort)
{return invitationRepository.findAllByInvitedChoir(choir);}


// find all pageable Invitation by choir
public Page<Invitation> findAllInvitationpageByEvent(Event event,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return invitationRepository.findAllByEvent(event,pages);
}  
// find  list Invitation by choir
public List<Invitation> findlistofInvitationpageByChoir(Event event)
{
    return invitationRepository.findAllByEvent(event);
}  
}
