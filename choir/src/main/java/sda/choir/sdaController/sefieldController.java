package sda.choir.sdaController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import sda.choir.Model.User;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirServices;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.FeedbackServices;
import sda.choir.Services.UserServices;

@Controller
@SessionAttributes("sefield")
public class sefieldController {
      public static User user=new User();
   @Autowired private  UserServices userServices;
   @Autowired private AnnouncementServices announcementServices;
   private static String message="";
   @Autowired private EventServices eventServices;
   // @Autowired private InvitationServices invitationServices;
   // @Autowired private OfferedContributionServices offeredContributionServices;
   @Autowired private ChoirServices choirServices;
   @Autowired private ContributionServices contributionServices;
   @Autowired private FeedbackServices feedbackServices;
   @Autowired private ChurchServices churchServices;
//
@PreAuthorize("hasRole('ROLE_FIELD')")     
@GetMapping(value="/field/home")
   public String getPage(Model model) throws Exception {
    model.addAttribute("sefield", user);
   List<User>userList=userServices.findAllUserJoinedChoir(null);
   List<User>allUser=userServices.findAllUser();
   int randomIndex=new Random().nextInt(1000);
   int userNotJoinedChoirpercent=(userList.size()*100)/allUser.size();
   int userJoinedChoirpercent=((allUser.size()-userList.size())*100)/allUser.size();
   model.addAttribute("totaluser", allUser);
   model.addAttribute("choirmember",(allUser.size()-userList.size()));
   model.addAttribute("nonsinger", userList);
   List<String>labels=Arrays.asList("User Joined Choir","User Joined Choir");
   List<Integer> dataSeries =Arrays.asList(userJoinedChoirpercent,userNotJoinedChoirpercent);
   List<String> randomColor=Arrays.asList("#"+randomIndex,"#"+randomIndex);
   model.addAttribute("labels", labels);
   System.out.println(labels);
   System.out.println(dataSeries);
   model.addAttribute("dataSeries", dataSeries);
   model.addAttribute("Randomcolors",randomColor);
    return "sefield/dashboard";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/choir")
   public String getChoirPage(Model model) throws Exception {
      model.addAttribute("field",churchServices.findById(user.getChurch().getId()));
      model.addAttribute("choirList", choirServices.findListofChoir());
    return "sefield/choir";
   }
//
@PreAuthorize("hasRole('ROLE_FIELD')")     
@GetMapping(value="/field/event")
   public String getEventPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="10") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
      model.addAttribute("eventpage", eventServices.findAllEventpage(page, size, sort));
    return "sefield/event";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/announcement")
   public String getAnnouncementPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="10") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
      model.addAttribute("annuncementPage", announcementServices.findAllAnnouncementpage(page, size, sort));
    return "sefield/announcement";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/finance")
   public String getFinancePage(Model model) throws Exception {
      model.addAttribute("contributions",churchServices.findById(user.getChurch().getId()));
    return "sefield/finance";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/financialoffering")
   public String getFinancialOfferingPage(Model model) throws Exception {
    return "sefield/financialoffering";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/user")
   public String getUserPage(Model model) throws Exception {
      model.addAttribute("field",churchServices.findById(user.getChurch().getId()));
      model.addAttribute("listOfFieldDistricts", churchServices.findListofChurchByChurchId(user.getChurch()));
      model.addAttribute("message",message);
      message="";
    return "sefield/user";
   }
   @PreAuthorize("hasRole('ROLE_FIELD')") 
   @PostMapping(value="/field/user")
   public String createDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_DISTRICT");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Duty added successfully";
      }
      return "redirect:/field/user";
   }
   @PreAuthorize("hasRole('ROLE_FIELD')") 
   @PostMapping(value="/field/user/remove")
   public String removeDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_USER");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Has been removed successfully";
      }
      return "redirect:/field/user";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/feedback")
   public String getFeedbackPage(Model model,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue="10") int size,@RequestParam(defaultValue = "createDate")String sortname) throws Exception {
      model.addAttribute("feedbackList",feedbackServices.findListOfPageFeedback(page, size, sortname));
    return "sefield/feedback";
   }
@PreAuthorize("hasRole('ROLE_FIELD')") 
@GetMapping(value="/field/account")
   public String getAccountPage(Model model) throws Exception {
    return "sefield/account";
   }
}
