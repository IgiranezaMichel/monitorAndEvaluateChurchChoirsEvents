package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sda.choir.Model.Church;
import sda.choir.sdaRepository.ChurchRepository;

@Service
public class ChurchServices {
@Autowired private ChurchRepository churchRepository;
public Church saveOrUpdateChurch(Church data)
{
    return churchRepository.save(data);
}
// delete church
public String deleteChurch(int id)
{
    try {
        Church church=this.findById(id);
        if(church!=null)
         {
            churchRepository.deleteById(id);
            return church.getName()+" deleted successfully";
         }
         else
         return "No such church found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All churches
public Page<Church>findAllpageableChurches(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return churchRepository.findAll(pages);}
// find All churches
public List<Church>findListofChurches()
{return churchRepository.findAll();}
// getChurch by id
public Church findById(int id)
{  return churchRepository.findById(id).orElse(null);}
// pageable list if churches
public Page<Church> findAllpageableChurchByChurchId(Church church,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return churchRepository.findAllBychurchId(church,pages);
}
public List<Church> findListofChurchByChurchId(Church church)
{
return churchRepository.findAllBychurchId(church);
}
public Church findByChurch(Church church)
{
    return churchRepository.findByChurchId(church);
}
// type
public Page<Church> findAllpageableChurchByType(String type,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return churchRepository.findAllByType(type,pages);
}
public List<Church> findListofChurchByType(String type)
{
return churchRepository.findAllByType(type);
}
// find pageable church by Location
public Page<Church> findAllpageableChurchByLocation(String location,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return churchRepository.findAllByLocation(location,pages);
}
// find church by Location
public List<Church> findListofChurchByLocation(String name)
{   return churchRepository.findAllByLocation(name);
}
// find pageable church by Location
public Page<Church> findAllpageableChurchByName(String location,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return churchRepository.findAllByName(location,pages);
}
// find church by Location
public List<Church> findListofChurchByName(String name)
{   return churchRepository.findAllByName(name);
}
}
