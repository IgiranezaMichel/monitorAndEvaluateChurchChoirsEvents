package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sda.choir.Model.Event;
import sda.choir.sdaRepository.EventRepository;
import sda.choir.Model.Choir;
@Service
public class EventServices {
     @Autowired private EventRepository eventRepository;
public Event saveOrUpdateEvent(Event data)
{
    return eventRepository.save(data);
}
// delete Event
public String deleteEvent(int id)
{
    try {
        Event Event=this.findById(id);
        if(Event!=null)
         {
            eventRepository.deleteById(id);
            return Event.getTitle()+" deleted successfully";
         }
         else
         return "No such Event found";
    } catch (Exception e) {
        return e.getMessage();
    }
}
// find All Event page
public Page<Event>findAllEventpage(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));return eventRepository.findAll(pages);}
// find All Event page
public List<Event>findListofEvent()
{return eventRepository.findAll();}

// get Event by id
public Event findById(int id)
{  return eventRepository.findById(id).orElse(null);}
public List<Event> findListofEvent(int page,int size,String sort)
{
return eventRepository.findAll();
}
public Page<Event> findAllpageableEvent(int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return eventRepository.findAll(pages);
}
// find Pageable Event by title
public Page<Event> findAllEventpageByTitle(String title,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
return eventRepository.findAllByTitle(title,pages);
}
// get list of Events
public List<Event> findListofEventByTitle(String title,int page,int size,String sort)
{return eventRepository.findAllByTitle(title);}


// find all pageable Event by choir
public Page<Event> findAllEventpageByChoir(Choir choir,int page,int size,String sort)
{Pageable pages=PageRequest.of(page, size, Sort.by(sort));
    return eventRepository.findAllByChoir(choir,pages);
}  
// find  list Event by choir
public List<Event> findlistofEventpageByChoir(Choir choir)
{
    return eventRepository.findAllByChoir(choir);
}  

}
