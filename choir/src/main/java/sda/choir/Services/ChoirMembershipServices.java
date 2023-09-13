package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.ChoirMembership;
import sda.choir.Model.User;
import sda.choir.Model.Choir;
import sda.choir.sdaRepository.ChoirMembershipRepository;
@Service
public class ChoirMembershipServices {
    @Autowired private ChoirMembershipRepository choirMembershipRepository;
public ChoirMembership saveOrUpdateChoirMembership(ChoirMembership data)
{
    return choirMembershipRepository.save(data);
}
public ChoirMembership findUserMembership(User user)
{
    return choirMembershipRepository.findByUser(user);
}
// delete ChoirMembership
public String deleteChoirMembership(int id)
{
    try {
        ChoirMembership ChoirMembership=this.findById(id);
        if(ChoirMembership!=null)
         {
            choirMembershipRepository.deleteById(id);
            return ChoirMembership.getUser().getName()+" Membership has been deleted successfully";
         }
         else
         return "No such ChoirMembership found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All ChoirMembership page
public Page<ChoirMembership>findAllChoirMembershippage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return choirMembershipRepository.findAll(pages);}
// find All ChoirMembership page
public List<ChoirMembership>findListofChoirMembership()
{return choirMembershipRepository.findAll();}

// get ChoirMembership by id
public ChoirMembership findById(int id)
{  return choirMembershipRepository.findById(id).orElse(null);}

// find all pageable ChoirMembership by choir
public Page<ChoirMembership> findAllChoirMembershippageByChoir(Choir choir,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return choirMembershipRepository.findAllByChoir(choir,pages);
}  
// find  list ChoirMembership by choir
public List<ChoirMembership> findlistofChoirMembershipByChoir(Choir choir)
{
    return choirMembershipRepository.findAllByChoir(choir);
}  
public List<ChoirMembership> findlistofmembersgipApprovedByChoirPresident(boolean isApproved)
{
    return choirMembershipRepository.findAllByIsApprovedByChoirPresident(isApproved);
}
}
