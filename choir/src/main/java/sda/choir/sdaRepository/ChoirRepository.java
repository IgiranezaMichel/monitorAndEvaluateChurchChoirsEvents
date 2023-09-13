package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.User;

public interface ChoirRepository extends JpaRepository<Choir,Integer>,RevisionRepository<Choir,Integer,Integer> {

    List<Choir> findAllByName(String title);

    Page<Choir> findAllByName(String title, Pageable pages);

    Page<Choir> findAllByChurch(Church church, Pageable pages);

    List<Choir> findAllByChurch(Church church);

    Choir findByLeader(User user);
    
}
