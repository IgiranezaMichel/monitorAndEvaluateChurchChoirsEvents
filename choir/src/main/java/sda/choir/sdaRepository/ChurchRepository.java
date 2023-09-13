package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Church;

public interface ChurchRepository extends JpaRepository<Church,Integer>,RevisionRepository<Church,Integer,Integer>{

    List<Church> findAllByLocation(String name);
    Page<Church> findAllByLocation(String name,Pageable pages);
    List<Church> findBychurchId(Church church);
    Page<Church> findAllBychurchId(Church church, Pageable pages);
    List<Church> findAllBychurchId(Church church);
    Page<Church> findAllByType(String type, Pageable pages);
    List<Church> findAllByType(String type);
    Page<Church> findAllByName(String location, Pageable pages);
    List<Church> findAllByName(String name);
    Church findByChurchId(Church church);
}
