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
import sda.choir.Model.Contribution;
import sda.choir.sdaRepository.ContributionRepository;

@Service
public class ContributionServices {
 @Autowired private ContributionRepository contributionRepository;
 public Contribution saveOrUpdateContribution(Contribution data)
{
    return contributionRepository.save(data);
}
// delete contribution
public String deleteContribution(int id)
{
    try {
        Contribution contribution=this.findById(id);
        if(contribution!=null)
         {
            contributionRepository.deleteById(id);
            return contribution.getTitle()+" deleted successfully";
         }
         else
         return "No such contribution found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Contributiones
public Page<Contribution>findAllContributiones(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return contributionRepository.findAll(pages);}
// getContribution by id
public Contribution findById(int id)
{  return contributionRepository.findById(id).orElse(null);}

public Page<Contribution> findAllContributionByTitle(Contribution Contribution,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return contributionRepository.findAllByTitle(Contribution.getTitle(),pages);
}
public List<Contribution>findAllContributions()
{
    return contributionRepository.findAll();
}
// find Contribution by Choir
public Page<Contribution> findAllpageableContributionByChoir(Choir choir,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return contributionRepository.findAllByChoir(choir,pages);
}
public List<Contribution> findListofContributionByChoir(Choir choir)
{
    return contributionRepository.findAllByChoir(choir);
}
public Page<Contribution> findAllPageableContributionByChurch(Church church,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return contributionRepository.findAllByChurch(church,pages);
}
public List<Contribution> findListofContributionByChurch(Church church)
{
    return contributionRepository.findAllByChurch(church);
}
}
