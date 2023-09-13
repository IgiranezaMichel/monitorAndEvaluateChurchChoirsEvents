package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.User;

public interface UserRepository extends JpaRepository<User,Integer>,RevisionRepository<User,Integer,Integer> {
    List<User> findAllByName(String name);
    Page<User> findAllByName(String name, Pageable pages);

    List<User> findAllByGender(String gender);
    Page<User> findAllByGender(User user, Pageable pages);

    User findByEmail(String email);
    List<User> findAllByChoir(Choir choir);
    Page<User> findAllByChoir(Choir choir, Pageable pages);
    List<User> findAllByChurch(Church church);
    Page<User> findAllByChurch(Church church, Pageable pages);
    User findByUsername(String username);
    
}
