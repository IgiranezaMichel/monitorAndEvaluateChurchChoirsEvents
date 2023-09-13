package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Announcement;
import sda.choir.Model.Choir;
import sda.choir.Model.Church;

public interface AnnouncementRepository extends JpaRepository<Announcement,Integer>,RevisionRepository<Announcement,Integer,Integer>{

    List<Announcement> findAllByTitle(String title);

    Page<Announcement> findAllByTitle(String title, Pageable pages);

    Page<Announcement> findAllByChoir(Choir choir, Pageable pages);

    Page<Announcement> findAllByChurch(Church church, Pageable pages);

    List<Announcement> findAllByChurch(Church church);

    List<Announcement> findAllByChoir(Choir choir);
    
}
