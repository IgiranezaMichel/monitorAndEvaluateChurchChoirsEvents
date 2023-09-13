package sda.choir.sdaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import sda.choir.Model.Choir;
import sda.choir.Model.ChoirMembership;
import sda.choir.Model.User;

public interface ChoirMembershipRepository extends JpaRepository<ChoirMembership,Integer>,RevisionRepository<ChoirMembership,Integer,Integer>{

    Page<ChoirMembership> findAllByChoir(Choir choir, Pageable pages);

    List<ChoirMembership> findAllByChoir(Choir choir);

    ChoirMembership findByUser(User user);

    List<ChoirMembership> findAllByIsApprovedByChoirPresident(boolean isApproved);
    
}
