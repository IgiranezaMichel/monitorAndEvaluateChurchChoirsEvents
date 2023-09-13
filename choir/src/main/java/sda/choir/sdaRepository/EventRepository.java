package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.Event;

public interface EventRepository extends JpaRepository<Event,Integer>,RevisionRepository<Event,Integer,Integer>{

    Page<Event> findAllByTitle(String title, Pageable pages);

    List<Event> findAllByTitle(String title);

    Page<Event> findAllByChoir(Choir choir, Pageable pages);

    List<Event> findAllByChoir(Choir choir);
    
}
