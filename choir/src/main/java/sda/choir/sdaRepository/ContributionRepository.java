package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.Contribution;

public interface ContributionRepository extends JpaRepository<Contribution,Integer>,RevisionRepository<Contribution,Integer,Integer>{

    Page<Contribution> findAllByTitle(String title, Pageable pages);

    Page<Contribution> findAllByChoir(Choir choir, Pageable pages);

    Page<Contribution> findAllByChurch(Church church, Pageable pages);

    List<Contribution> findAllByChoir(Choir choir);

    List<Contribution> findAllByChurch(Church church);
    
}
