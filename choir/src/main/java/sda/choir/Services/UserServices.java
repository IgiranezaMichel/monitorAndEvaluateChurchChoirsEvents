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
import sda.choir.sdaRepository.UserRepository;
@Service
public class UserServices {
    @Autowired private UserRepository userRepository;
public User saveOrUpdateUser(User data)
{
    return userRepository.save(data);
}
// delete contribution
public String deleteUser(int id)
{
    try {
        User user=this.findById(id);
        if(user!=null)
         {
            userRepository.deleteById(id);
            return user.getName()+" deleted successfully";
         }
         else
         return "No such contribution found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Users
public Page<User>findAllUserpage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return userRepository.findAll(pages);}
public List<User>findAllUser()
{return userRepository.findAll();}
 
// getUser by id
public User findById(int id) throws Exception
{  return userRepository.findById(id).orElse(null);}

public Page<User> findAllUserByNamepage(String name,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return userRepository.findAllByName(name,pages);
}
// find all list by name
public List<User>findAllUserByName(String name)
{
    return userRepository.findAllByName(name);
}
// find User by Location
public Page<User> findAllUserByGenderpage(User User,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return userRepository.findAllByGender(User,pages);
}
// find List by gender
public List<User>findAllUserByGender(String gender)
{
    return userRepository.findAllByGender(gender);
}
public User findUserByEmail(String email)
{return userRepository.findByEmail(email);}
// user list
public List<User>findAllUserJoinedChoir(Choir choir)
{ return userRepository.findAllByChoir(choir);}
// pageable list of choir member
public Page<User>findAllUserJoinedChoirpage(Choir choir,int page,int size,String sort)
{   Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return userRepository.findAllByChoir(choir,pages);}
    // list of church member
public List<User>findListOfUserJoinedChurch(Church church)
{   return userRepository.findAllByChurch(church);}
// pageable list of church member
public Page<User>findAllUserJoinedChurchpage(Church church,int page,int size,String sort)
{   Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return userRepository.findAllByChurch(church,pages);}
}
