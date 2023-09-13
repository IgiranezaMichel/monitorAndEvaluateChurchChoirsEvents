package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.User;
import sda.choir.sdaRepository.ChoirRepository;

@Service
public class ChoirServices {
    @Autowired private ChoirRepository choirRepository;
public Choir saveOrUpdateChoir(Choir data)
{
    return choirRepository.save(data);
}
// delete choir
public String deleteChoir(int id)
{
    try {
        Choir choir=this.findChoirById(id);
        if(choir!=null)
         {
            choirRepository.deleteById(id);
            return choir.getName()+" deleted successfully";
         }
         else
         return "No such Choir found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Choir page
public Page<Choir>findAllChoirpage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return choirRepository.findAll(pages);}
// find All Choir 
public List<Choir>findListofChoir()
{return choirRepository.findAll();}

// get Choir by id
public Choir findChoirById(int id)
{  return choirRepository.findById(id).orElse(null);}

// find Pageable Choir by title
public Page<Choir> findAllChoirpageByName(String name,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return choirRepository.findAllByName(name,pages);
}
// get list of Choirs
public List<Choir> findListofChoirByName(String name)
{return choirRepository.findAllByName(name);}


// find all pageable Choir by choir
public Page<Choir> findAllChoirpageByChurch(Church church,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return choirRepository.findAllByChurch(church,pages);
}  
public List<Choir> findListofChoirByChurch(Church church)
{ return choirRepository.findAllByChurch(church);
} 
// find  list Choir by choir
public Choir findChoirLeader(User user)
{
    return choirRepository.findByLeader(user);
}   
}
