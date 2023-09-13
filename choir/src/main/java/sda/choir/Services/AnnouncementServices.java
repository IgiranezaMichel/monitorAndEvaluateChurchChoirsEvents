package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.Announcement;
import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.sdaRepository.AnnouncementRepository;
@Service
public class AnnouncementServices {
 @Autowired private AnnouncementRepository announcementRepository;
public Announcement saveOrUpdateAnnouncement(Announcement data)
{
    return announcementRepository.save(data);
}
// delete announcement
public String deleteAnnouncement(int id)
{
    try {
        Announcement announcement=this.findById(id);
        if(announcement!=null)
         {
            announcementRepository.deleteById(id);
            return announcement.getTitle()+" deleted successfully";
         }
         else
         return "No such announcement found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Announcement page
public Page<Announcement>findAllAnnouncementpage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return announcementRepository.findAll(pages);}
// find All Announcement page
public List<Announcement>findListofAnnouncement()
{return announcementRepository.findAll();}

// get Announcement by id
public Announcement findById(int id)
{  return announcementRepository.findById(id).orElse(null);}

// find Pageable announcement by title
public Page<Announcement> findAllAnnouncementpageByTitle(String title,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return announcementRepository.findAllByTitle(title,pages);
}
// get list of announcements
public List<Announcement> findListofAnnouncementByTitle(String title,int page,int size,String sort)
{return announcementRepository.findAllByTitle(title);}


// find all pageable Announcement by choir
public Page<Announcement> findAllAnnouncementpageByChoir(Choir choir,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return announcementRepository.findAllByChoir(choir,pages);
}  
// find  list Announcement by choir
public List<Announcement> findlistofAnnouncementByChoir(Choir choir)
{
    return announcementRepository.findAllByChoir(choir);
}  

// find all pageable Announcement by church
public Page<Announcement> findAllAnnouncementpageByChurch(Church church,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return announcementRepository.findAllByChurch(church,pages);
} 
// find all list Announcement by church
public List<Announcement> findListofChurchAnnouncement(Church church)
{ return announcementRepository.findAllByChurch(church);} 
}
