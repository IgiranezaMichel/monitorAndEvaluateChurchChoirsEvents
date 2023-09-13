package sda.choir.sdaController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import sda.choir.Model.Church;
import sda.choir.Model.User;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.UserServices;
@Controller
@SessionAttributes("pastor")
public class PastorController {
     public static User user=new User();
   @Autowired private  UserServices userServices;
   @Autowired private ChurchServices churchServices;
   public static String message="";
// 
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/home")
   public String getPage(Model model) throws Exception {
    model.addAttribute("pastor", user);
   //  List<Church>churchList=churchServices.findListofChurchByChurchId(user.getChurch());
   //  List<User>allUsers=churchList.
   // List<User>allUser=userServices.findAllUser();
   // int userNotJoinedChoirpercent=(userList.size()*100)/allUser.size();
   // int userJoinedChoirpercent=((allUser.size()-userList.size())*100)/allUser.size();
   // model.addAttribute("totaluser", allUser.size());
   // model.addAttribute("choirmember",(allUser.size()-userList.size()));
   // model.addAttribute("nonsinger", userList.size());
   // List<String>labels=Arrays.asList("Singers","Upcomers ");
   // List<Integer> dataSeries =Arrays.asList(userJoinedChoirpercent,userNotJoinedChoirpercent);
   // model.addAttribute("labels", labels);
   // model.addAttribute("dataSeries", dataSeries);
    return "pastor/dashboard";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/church")
   public String getChurchPage(Model model) throws Exception {
      model.addAttribute("churhList", churchServices.findListofChurchByChurchId(user.getChurch()));
    return "pastor/church";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/choir")
   public String getChoirPage(Model model) throws Exception {
      model.addAttribute("districtChurches",churchServices.findById(user.getChurch().getId()));
    return "pastor/choir";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")     
@GetMapping(value="/pastor/event")
   public String getEventPage(Model model) throws Exception {
      model.addAttribute("eventList", churchServices.findListofChurchByChurchId(user.getChurch()));
    return "pastor/event";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/announcement")
   public String getAnnouncementPage(Model model) throws Exception {
   model.addAttribute("churhList", churchServices.findListofChurchByChurchId(user.getChurch()));
    return "pastor/announcement";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/finance")
   public String getFinancePage(Model model) throws Exception {
      model.addAttribute("financeList", churchServices.findListofChurchByChurchId(user.getChurch()));
    return "pastor/finance";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/user")
   public String getUserPage(Model model) throws Exception {
      model.addAttribute("userList", churchServices.findListofChurchByChurchId(user.getChurch()));
      model.addAttribute("allUsers", userServices.findAllUser());
      model.addAttribute("listOfDistrictchurche", churchServices.findListofChurchByChurchId(user.getChurch()));
      model.addAttribute("message",message);
      message="";
    return "pastor/user";
   }
   @PreAuthorize("hasRole('ROLE_DISTRICT')")  
   @PostMapping(value="/pastor/user")
   public String createDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_CHURCH");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Duty added successfully";
      }
      return "redirect:/pastor/user";
   }
   @PreAuthorize("hasRole('ROLE_DISTRICT')")  
   @PostMapping(value="/pastor/user/remove")
   public String removeDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_USER");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Has been removed successfully";
      }
      return "redirect:/pastor/user";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/feedback")
   public String getFeedbackPage(Model model) throws Exception {
      model.addAttribute("feedbackList", churchServices.findListofChurchByChurchId(user.getChurch()));
    return "pastor/feedback";
   }
@PreAuthorize("hasRole('ROLE_DISTRICT')")  
@GetMapping(value="/pastor/account")
   public String getAccountPage(Model model) throws Exception {
    return "pastor/account";
   }
}
