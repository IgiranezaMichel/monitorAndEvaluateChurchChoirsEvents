package sda.choir.sdaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Contribution;
import sda.choir.Model.OfferedContribution;
import sda.choir.Model.User;

public interface OfferedContributionRepository extends JpaRepository<OfferedContribution,Integer>,RevisionRepository<OfferedContribution,Integer,Integer>{

    Page<OfferedContribution> findAllByContribution(Contribution contribution, Pageable pages);

    Page<OfferedContribution> findAllByUser(User user, Pageable pages);
    
}
