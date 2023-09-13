package sda.choir.securityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sda.choir.Model.User;
import sda.choir.sdaRepository.UserRepository;
@Service
public class UserDetailService implements UserDetailsService{
@Autowired private UserRepository userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        User  User =userrepo.findByUsername(username);
        if(User == null)throw new UsernameNotFoundException("Unimplemented method  loadUserByUsername");
    return new UserDetailPrinciple(User);
    }
}
