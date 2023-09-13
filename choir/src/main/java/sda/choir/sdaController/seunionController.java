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

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Model.User;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirServices;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.FeedbackServices;
import sda.choir.Services.OfferedContributionServices;
// import sda.choir.Services.InvitationServices;
// import sda.choir.Services.OfferedContributionServices;
import sda.choir.Services.UserServices;

@Controller
@SessionAttributes("seunion")
public class seunionController {
    public static User user=new User();
   @Autowired private  UserServices userServices;
   @Autowired private AnnouncementServices announcementServices;
   private static String message="";
   @Autowired private EventServices eventServices;
   // @Autowired private InvitationServices invitationServices;
   // @Autowired private OfferedContributionServices offeredContributionServices;
   @Autowired private ChoirServices choirServices;
   @Autowired private FeedbackServices feedbackServices;
   @Autowired private ChurchServices churchServices;

//    
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/home")
   public String getPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
    user=userServices.findById(1);
    model.addAttribute("seunion", user);
     List<User>userList=userServices.findAllUserJoinedChoir(null);
   List<User>allUser=userServices.findAllUser();
   int randomIndex=new Random().nextInt(1000);
   int userNotJoinedChoirpercent=(userList.size()*100)/allUser.size();
   int userJoinedChoirpercent=((allUser.size()-userList.size())*100)/allUser.size();
   model.addAttribute("totaluser", allUser.size());
   model.addAttribute("choirmember",(allUser.size()-userList.size()));
   model.addAttribute("nonsinger", userList.size());
   List<String>labels=Arrays.asList("Singers","Upcomers ");
   List<Integer> dataSeries =Arrays.asList(userJoinedChoirpercent,userNotJoinedChoirpercent);
   List<String> randomColor=Arrays.asList("#"+randomIndex,"#"+randomIndex);
   model.addAttribute("labels", labels);
   System.out.println(labels);
   System.out.println(dataSeries);
   model.addAttribute("dataSeries", dataSeries);
   model.addAttribute("Randomcolors",randomColor);
    return "seunion/dashboard";
   }
   // church
   @PreAuthorize("hasRole('ROLE_UNION')")
   @GetMapping(value="/union/church")
   public String getChrchPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("fieldPage",churchServices.findListofChurchByType("FIELD"));
      model.addAttribute("districtList",churchServices.findListofChurchByType("DISTRICT"));
      model.addAttribute("churchList",churchServices.findListofChurchByType("CHURCH"));
    return "seunion/church";
   }
    @PreAuthorize("hasRole('ROLE_UNION')")
    @PostMapping(value ="/union/church")
   public String createOrUpdateChurchPage(@ModelAttribute Church church) throws Exception {
      Church ann=churchServices.findById(church.getId());
      Church church2=churchServices.saveOrUpdateChurch(church);
      if(ann==null)
       message=church2.getName()+" "+church2.getType()+" Saved Sucessfully";
       else message=church2.getName()+" "+church2.getType()+" Updated Sucessfully"; 
    return "redirect:/union/church";
   }
   @PreAuthorize("hasRole('ROLE_UNION')")
   @PostMapping(value ="/union/church/delete")
   public String deleteAnnouncement(@ModelAttribute Church church) throws Exception {
      Church ann=churchServices.findById(church.getId());
      if(ann==null)
       message="No such announcement";
       else {
         announcementServices.deleteAnnouncement(ann.getId());
         message=ann.getName()+" Deleted Sucessfully"; 
       }
    return "redirect:/union/church";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/choir")
   public String getChoirPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("fieldPage",churchServices.findListofChurchByType("FIELD"));
      model.addAttribute("districtList",churchServices.findListofChurchByType("DISTRICT"));
      model.addAttribute("churchList",churchServices.findListofChurchByType("CHURCH"));
      model.addAttribute("totalChoir",choirServices.findListofChoir());
      model.addAttribute("churchLists",churchServices.findListofChurchByType("FIELD"));
      model.addAttribute("choirList", choirServices.findListofChoir());
    return "seunion/choir";
   }
//   
@PreAuthorize("hasRole('ROLE_UNION')") 
@GetMapping(value="/union/event")
   public String getEventPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("eventpage", eventServices.findAllEventpage(page, size, sort));
      model.addAttribute("eventList", eventServices.findListofEvent());
    return "seunion/event";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/announcement")
   public String getAnnouncementPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="creationDate") String sort) throws Exception {
      model.addAttribute("announcementPage", announcementServices.findAllAnnouncementpage(page, size, sort));
      model.addAttribute("announcementList",announcementServices.findListofAnnouncement());
    return "seunion/announcement";
   }
@Autowired private OfferedContributionServices offerContribution;
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/finance")
   public String getFinancePage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
      model.addAttribute("fieldPage",churchServices.findListofChurchByType("FIELD"));
      model.addAttribute("districtList",churchServices.findListofChurchByType("DISTRICT"));
      model.addAttribute("churchList",churchServices.findListofChurchByType("CHURCH"));
      model.addAttribute("contributionList",offerContribution.findAllContribution());
    return "seunion/finance";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/financialoffering")
   public String getFinancialOfferingPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      //  model.addAttribute("offerPage",offeredContributionServices.findAllByContribution(page, size, sort));
      // model.addAttribute("offerList",contributionServices.findAllContributions());
      return "seunion/financialoffering";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/user")
   public String getUserPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="choir") String sort) throws Exception {
     model.addAttribute("userpage",userServices.findAllUserpage(page, size, sort));
      model.addAttribute("userList",userServices.findAllUser());
      model.addAttribute("userWithNoChoir",userServices.findAllUserJoinedChoir(null));
      model.addAttribute("fieldList",churchServices.findListofChurchByType("FIELD"));
      model.addAttribute("message",message);
      message="";
    return "seunion/user";
   }
   @PreAuthorize("hasRole('ROLE_UNION')")
   @PostMapping(value = "/union/user")
   public String assignDuty(@ModelAttribute User user) {
      try {
         User usr=userServices.findById(user.getId());
         if(usr==null) message="User not found";
         else
         {
            usr.setRole("ROLE_FIELD");
            User currentUser=userServices.saveOrUpdateUser(usr);
            message=currentUser.getName()+" Duty added sucessfully";
            
         }
      } catch (Exception e) {message=e.getMessage();
   }
    return "redirect:/union/user";
   }
   @PreAuthorize("hasRole('ROLE_UNION')")
   @PostMapping(value = "/union/user/remove")
   public String removeassignDuty(@ModelAttribute User user) {
      try {
         User usr=userServices.findById(user.getId());
         if(usr==null) message="User not found";
         else
         {
            usr.setRole("ROLE_USER");
            User currentUser=userServices.saveOrUpdateUser(usr);
            message=currentUser.getName()+" Removed sucessfully";
            
         }
      } catch (Exception e) {message=e.getMessage();
   }
    return "redirect:/union/user";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/feedback")
   public String getFeedbackPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("feedbackList",feedbackServices.findListOfPageFeedback(page, size, sort));
    return "seunion/feedback";
   }
@PreAuthorize("hasRole('ROLE_UNION')")
@GetMapping(value="/union/account")
   public String getAccountPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
    return "seunion/account";
   }
}
