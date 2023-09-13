package sda.choir.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.Feedback;
import sda.choir.sdaRepository.FeedbackRepository;

@Service
public class FeedbackServices {
  @Autowired private FeedbackRepository feedbackRepository;
  public Feedback saveOrUpdateFeedback(Feedback feedback) 
  {
    return feedbackRepository.save(feedback);
  }     
  public String deleteFeedback(Feedback feedback)
  {String message="Feedback deleted successfully";
    try {
        feedbackRepository.delete(feedback);
    } catch (Exception e) {
        message=e.getMessage();
    }
    return message;
  }
  public Feedback findFeedbackById(int id){return feedbackRepository.findById(id).orElse(null);}
  public Page<Feedback> findListOfPageFeedback(int page,int size,String sortName)
  {
    Pageable pages=PageRequest.of(page, size,Sort.by(sortName).descending());
    return feedbackRepository.findAll(pages);
  }
  public List<Feedback> findAllFeedbackByChoir(Choir choir)
  {
    return feedbackRepository.findAllByChoir(choir);
  }
  public List<Feedback> findAllFeedbackByUser(User user)
  {
    return feedbackRepository.findAllByUser(user);
  }
  public List<Feedback> findAllFeedbackByChurch(Church church)
  {
    return feedbackRepository.findAllByChurch(church);
  }
}
