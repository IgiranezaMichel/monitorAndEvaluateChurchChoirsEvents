package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.Contribution;
import sda.choir.Model.OfferedContribution;
import sda.choir.Model.User;
import sda.choir.sdaRepository.OfferedContributionRepository;
@Service
public class OfferedContributionServices {
    @Autowired private OfferedContributionRepository ocRepository;
    public OfferedContribution saveOrUpdateOfferedContribution(OfferedContribution data)
{ return ocRepository.save(data);}
// delete contribution
public String deleteOfferedContribution(int id)
{
    try {
        OfferedContribution contribution=this.findById(id);
        if(contribution!=null)
         {
            ocRepository.deleteById(id);
            return contribution.getUser()+" removed from "+contribution.getContribution().getTitle();
         }
         else
         return "No such contribution found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All OfferedContribution
public Page<OfferedContribution>findAllpageableOfferedContributions(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return ocRepository.findAll(pages);}
public List<OfferedContribution>findAllContribution()
{
    return ocRepository.findAll();
}
// get OfferedContribution by id
public OfferedContribution findById(int id)
{  return ocRepository.findById(id).orElse(null);}
// find all by contribution
public Page<OfferedContribution> findAllByContribution(Contribution contribution,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return ocRepository.findAllByContribution(contribution,pages);
}
// find OfferedContribution by user
public Page<OfferedContribution> findAllOfferedContributionByUser(User user,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return ocRepository.findAllByUser(user,pages);
}
}
