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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import sda.choir.Model.Announcement;
import sda.choir.Model.Choir;
import sda.choir.Model.ChoirMembership;
import sda.choir.Model.Church;
import sda.choir.Model.Contribution;
import sda.choir.Model.Feedback;
import sda.choir.Model.Invitation;
import sda.choir.Model.User;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirMembershipServices;
import sda.choir.Services.ChoirServices;
// import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.FeedbackServices;
import sda.choir.Services.InvitationServices;
import sda.choir.Services.OfferedContributionServices;
import sda.choir.Services.UserServices;
@Controller
@SessionAttributes("churchAdmin")
public class ChurchAdminController {
     public static User user=new User();
   @Autowired private  UserServices userServices;
   @Autowired private AnnouncementServices announcementServices;
   public static String message="";
   @Autowired private EventServices eventServices;
   @Autowired private OfferedContributionServices offeredContributionServices;
   @Autowired private ChoirServices choirServices;
   @Autowired private ContributionServices contributionServices;
   @Autowired private FeedbackServices feedbackServices;
   @Autowired private InvitationServices invitationServices;
//
// @Autowired private ChurchServices churchServices;
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/home")
   public String getPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="invitedChoir") String sort) throws Exception {
    model.addAttribute("churchAdmin", user);
      List<User>churchMembers=userServices.findListOfUserJoinedChurch(user.getChurch());
      if(churchMembers.size()==0)
         churchMembers.add(1,new User());
   List<User> upcomers = churchMembers.stream()
                                  .filter(user -> user.getChoir() == null)
                                  .collect(Collectors.toList());
   List<User> choirMember = churchMembers.stream()
                                  .filter(user -> user.getChoir() != null)
                                  .collect(Collectors.toList());
   int randomIndex=new Random().nextInt(1000);
   int upcomerUserPercent=(upcomers.size()*100)/churchMembers.size();
   int userJoinedChoirpercent=((choirMember.size())*100)/churchMembers.size();
   model.addAttribute("churchmember", churchMembers.size());
   model.addAttribute("choirmember",choirMember.size());
   model.addAttribute("nonsinger",upcomers.size());
   List<String>labels=Arrays.asList("Singers","Upcomers ");
   List<Integer> dataSeries =Arrays.asList(userJoinedChoirpercent,upcomerUserPercent);
   List<String> randomColor=Arrays.asList("#"+randomIndex,"#"+randomIndex);
   model.addAttribute("labels", labels);
   model.addAttribute("dataSeries", dataSeries);
   model.addAttribute("Randomcolors",randomColor);
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/dashboard";
   }
   // choir
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/choir")
   public String getChoirPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="5") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    model.addAttribute("choirpage",choirServices.findAllChoirpageByChurch(user.getChurch(), page, size, sort));
   //  model.addAttribute("churchChoirs",choirServices.findListofChoirByChurch(user.getChurch()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/choir";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value ="/church/choir")
   public String createOrUpdateChoirPage(@ModelAttribute Choir choir) throws Exception {
      Choir chr=choirServices.findChoirById(choir.getId());
      if(chr==null)
       message=choir.getName()+" Saved Sucessfully";
       else{ choir.setLeader(chr.getLeader()); message=chr.getName()+" Updated Sucessfully"; }
       choirServices.saveOrUpdateChoir(choir);
    return "redirect:/church/choir";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value="/church/choir/duty")
   public String createDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_CHOIR");
         User res=userServices.saveOrUpdateUser(usr);
         Choir cr=choirServices.findChoirById(res.getChoir().getId());
         cr.setLeader(res);
         choirServices.saveOrUpdateChoir(cr);
         message=res.getName()+" Duty added successfully";
      }
      return "redirect:/church/choir";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value="/church/choir/duty/remove")
   public String removeDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_SINGER");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Has been removed successfully";
      }
      return "redirect:/church/choir";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value ="/church/choir/delete")
   public String deleteChoir(@ModelAttribute Choir choir) throws Exception {
      Choir cr=choirServices.findChoirById(choir.getId());
      if(cr==null)
       message="No such Choir";
       else {
         choirServices.deleteChoir(cr.getId());
         message=cr.getName()+" Deleted Sucessfully"; 
       }
    return "redirect:/church/choir";
   }
   @Autowired private ChoirMembershipServices choirMembershipServices;
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @GetMapping(value="/church/membership")
   public String getMembershipPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
   //  model.addAttribute("membershipList", choirMembershipServices.findlistofmembersgipApprovedByChoirPresident(true));
   model.addAttribute("choirpage",choirServices.findAllChoirpageByChurch(user.getChurch(), page, size, sort));
    model.addAttribute("Message", message);
    message="";
    return "churchAdmin/membership";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value = "/church/membership/{approved}")
   public String createMembership(@ModelAttribute  ChoirMembership choirMembership,@PathVariable boolean approved)
   { ChoirMembership membership=choirMembershipServices.findById(choirMembership.getId());
     membership.setAllowed(approved);membership.setApprovedByChurchElder(approved);
     ChoirMembership member=new ChoirMembership();
      if(membership!=null){  
         member=choirMembershipServices.saveOrUpdateChoirMembership(membership);
       }
      if(approved){
         try {
            User user=userServices.findById(membership.getUser().getId());
         user.setChoir(membership.getChoir());
         userServices.saveOrUpdateUser(user);
         message=member.getUser().getName()+" membership has been approved";
         } catch (Exception e) {
            message=member.getUser().getName()+" membership has been approved";
         }
      }
      else message=member.getUser().getName()+" membership has been denied";
      return "redirect:/church/membership";
   }
// announcement
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/event")
   public String getEventPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
    model.addAttribute("eventpage", eventServices.findAllEventpage(page, size, sort));
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/event";
   }
   // Announcement page
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/announcement")
   public String getAnnouncementPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
    model.addAttribute("announcementpage", announcementServices.findAllAnnouncementpageByChurch(user.getChurch(), page, size, sort));
    model.addAttribute("totalannouncement", announcementServices.findListofChurchAnnouncement(user.getChurch()));
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/announcement";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value ="/church/announcement")
   public String createOrUpdateAnnouncementPage(@ModelAttribute Announcement announcement) throws Exception {
      Announcement ann=announcementServices.findById(announcement.getId());
      Announcement announce=announcementServices.saveOrUpdateAnnouncement(announcement);
      if(ann==null)
       message=announce.getTitle()+" Saved Sucessfully";
       else message=announce.getTitle()+" Updated Sucessfully"; 
    return "redirect:/church/announcement";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value ="/church/announcement/delete")
   public String deleteAnnouncement(@ModelAttribute Announcement announcement) throws Exception {
      Announcement ann=announcementServices.findById(announcement.getId());
      if(ann==null)
       message="No such announcement";
       else {
         announcementServices.deleteAnnouncement(ann.getId());
         message=ann.getTitle()+" Updated Sucessfully"; 
       }
    return "redirect:/church/announcement";
   }
   // finance
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/finance")
   public String getFinancePage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="7") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
    model.addAttribute("contributionpage",contributionServices.findAllPageableContributionByChurch(user.getChurch(), page, size, sort));
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
   model.addAttribute("churchContributions",contributionServices.findListofContributionByChurch(user.getChurch()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/finance";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value="/church/finance")
   public String createContribution(@ModelAttribute Contribution contribution)
   {Contribution contr=contributionServices.findById(contribution.getId());
      Contribution ctn=contributionServices.saveOrUpdateContribution(contribution);
      if(contr==null)
       message=ctn.getTitle()+" Saved Sucessfully";
       else message=ctn.getTitle()+" Updated Sucessfully"; 
      return "redirect:/church/finance";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @PostMapping(value="/church/finance/delete")
   public String deleteContribution(@ModelAttribute Contribution contribution)
   {Contribution contr=contributionServices.findById(contribution.getId());
      if(contr==null) message=" This contribution does't exist";
       else{contributionServices.deleteContribution(contr.getId()); message=contr.getTitle()+" Deleted Sucessfully"; }
      return "redirect:/church/finance";
   }
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/financialoffering")
   public String getFinancialOfferingPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="contribution") String sort) throws Exception {
    model.addAttribute("offerContributionpage", offeredContributionServices.findAllpageableOfferedContributions(page, size, sort));
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/financialoffering";
   }
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/user")
   public String getUserPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="20") int size,@RequestParam(defaultValue ="choir") String sort) throws Exception {
    model.addAttribute("churchmember",userServices.findAllUserJoinedChurchpage(user.getChurch(), page, size, sort));
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    model.addAttribute("choirList",choirServices.findListofChoirByChurch(user.getChurch()));
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/user";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')") 
   @PostMapping(value="/church/user")
   public String createUserDuty(@RequestParam int userId,@RequestParam int choirId) throws Exception
   { User usr=userServices.findById(userId);
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_CHOIR");
         Choir choir=choirServices.findChoirById(choirId);
         usr.setChoir(choir);
         User res=userServices.saveOrUpdateUser(usr);
         
         choir.setLeader(usr);
         choirServices.saveOrUpdateChoir(choir);
         message=res.getName()+" Duty added successfully";
      }
      return "redirect:/church/user";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')") 
   @PostMapping(value="/church/user/remove")
   public String removeUserDuty(@ModelAttribute User user) throws Exception
   { User usr=userServices.findById(user.getId());
      if(usr==null) message="User not found";
      else{
         usr.setRole("ROLE_USER");
         User res=userServices.saveOrUpdateUser(usr);
         message=res.getName()+" Has been removed successfully";
      }
      return "redirect:/church/user";
   }
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/feedback")
   public String getFeedbackPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="invitedChoir") String sort) throws Exception {
    model.addAttribute("Message", message);
   //  model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
   //  model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
     model.addAttribute("feedbackList",feedbackServices.findAllFeedbackByChurch(user.getChurch()));
    message="";
    return "churchAdmin/feedback";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
    @PostMapping(value="/church/feedback")
   public String createFeedback(@ModelAttribute Feedback feedback){
      feedbackServices.saveOrUpdateFeedback(feedback);
      message="Feedback saved successfully";
    return "redirect:/church/feedback";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
    @PostMapping(value="/church/feedback/delete")
   public String deleteFeedback(@ModelAttribute Feedback feedback){
      message=feedbackServices.deleteFeedback(feedback);
    return "redirect:/church/feedback";
   }
   @PreAuthorize("hasRole('ROLE_CHURCH')")
   @GetMapping(value="/church/invitation")
   public String getInvitationPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="invitedChoir") String sort) throws Exception {
    model.addAttribute("Message", message);
    model.addAttribute("totalChurchmember",userServices.findListOfUserJoinedChurch(user.getChurch()));
    model.addAttribute("totalchoirmember",userServices.findAllUserJoinedChoir(user.getChoir()));
    message="";
    return "churchAdmin/invitation";
   }
@PreAuthorize("hasRole('ROLE_CHURCH')")
@GetMapping(value="/church/account")
   public String getAccountPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="invitedChoir") String sort) throws Exception {
    model.addAttribute("message", message);
    message="";
    return "churchAdmin/account";
   }

   @PostMapping(value="/church/invite/{approval}")
   public String createInvitation(@ModelAttribute Invitation invitation,@PathVariable String approval)
   {  Invitation invitationdb=invitationServices.findById(invitation.getId());
      if(invitationdb!=null)
      {  Church destinationChurch=invitationdb.getInvitedChoir().getChurch();
         Church actuaChurch=user.getChurch();
         boolean areInSameChurch=(actuaChurch.getId()==destinationChurch.getId());
         boolean areInSameDistrict=(actuaChurch.getChurchId().getId()==destinationChurch.getChurchId().getId());
         boolean areInSameField=(actuaChurch.getChurchId().getChurchId()==destinationChurch.getChurchId().getChurchId());
         if(((areInSameChurch) ||(areInSameDistrict))&&approval.equals("yes"))
         {invitationdb.setApprovalTarget("CHURCH");
         invitationdb.setAllowed(true);
         invitationdb.setApprovalLevel("CHURCH");
         message=invitationdb.getInvitedChoir().getName()+" has been approved successfully";
         }
         else if(((areInSameChurch) ||(areInSameDistrict))&&approval.equals("no"))
         {
          invitationdb.setApprovalTarget("CHURCH");
         invitationdb.setAllowed(false);
         invitationdb.setCanceled(true);
         invitationdb.setApprovalLevel("CHURCH");
         message=invitationdb.getInvitedChoir().getName()+" has been Cancelled successfully";
         }
         else if(!areInSameDistrict&&areInSameField)
          invitationdb.setApprovalTarget("DISTRICT");
         else if(!areInSameField)
         {
            invitationdb.setApprovalTarget("FIELD");
         }
        invitationServices.saveOrUpdateInvitation(invitationdb);
      }
      else{
         message="Please select a choir for invitation";
      }
      return "redirect:/church/event";
}
}
