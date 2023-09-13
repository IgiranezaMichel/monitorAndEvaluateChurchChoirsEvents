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
import sda.choir.Model.Event;
import sda.choir.Model.Feedback;
import sda.choir.Model.Invitation;
import sda.choir.Model.OfferedContribution;
import sda.choir.Model.User;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirMembershipServices;
import sda.choir.Services.ChoirServices;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.FeedbackServices;
import sda.choir.Services.InvitationServices;
import sda.choir.Services.OfferedContributionServices;
import sda.choir.Services.UserServices;
import sda.choir.securityConfiguration.EmailSenderServiceConfig;
@Controller
@SessionAttributes("choirAdmin")
public class ChoirAdminController {
     public static User user=new User();
   @Autowired private  UserServices userServices;
   @Autowired private AnnouncementServices announcementServices;
   private static String message="";
   @Autowired private EventServices eventServices;
   @Autowired private InvitationServices invitationServices;
   @Autowired private OfferedContributionServices offeredContributionServices;
   @Autowired private ChoirMembershipServices choirMembershipServices;
   @Autowired private FeedbackServices feedbackServices;
   // @Autowired private 
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/home")
   public String getPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="1") int size,@RequestParam(defaultValue ="invitedChoir") String sort) throws Exception {
    model.addAttribute("choirAdmin", user);
    model.addAttribute("lastinvitation",invitationServices.findAllInvitationpage(page, size, sort));
    model.addAttribute("offeredContribution",offeredContributionServices.findAllpageableOfferedContributions(page,size,"createDate"));
     List<User>totalChoirMembers=userServices.findAllUserJoinedChoir(user.getChoir());
     List<User>churchMembers=userServices.findListOfUserJoinedChurch(user.getChurch());
      if(totalChoirMembers.size()==0)
         totalChoirMembers.add(1,new User());
   List<User> upcomers = churchMembers.stream()
                                  .filter(user -> user.getChoir() == null)
                                  .collect(Collectors.toList());
   List<User> choirMember = churchMembers.stream()
                                  .filter(user -> user.getChoir() != null)
                                  .collect(Collectors.toList());
   double choirMemberPercent=(totalChoirMembers.size()*100)/churchMembers.size();
   double upcomerUserPercent=(upcomers.size()*100)/churchMembers.size();
   double userJoinedChoirpercent=((choirMember.size())*100)/churchMembers.size();
   model.addAttribute("churchmember", churchMembers.size());
   model.addAttribute("choirmember",choirMember.size());
   model.addAttribute("upcomers",upcomers.size());
   List<String>labels=Arrays.asList("Total Church Singers","Upcomers ",user.getChoir().getName()+" Members %");
   List<Double> dataSeries =Arrays.asList(userJoinedChoirpercent,upcomerUserPercent,choirMemberPercent);
   model.addAttribute("labels", labels);
   model.addAttribute("dataSeries", dataSeries);
    model.addAttribute("message", message);
    message="";
    return "choirAdmin/dashboard";
   }
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/choir")
   public String getChoirPage(Model model) throws Exception {
    return "choirAdmin/choir";
   }
// event
@Autowired private ChurchServices churchServices;
@PreAuthorize("hasRole('ROLE_CHOIR')")   
@GetMapping(value="/choir/event")
   public String getEventPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("eventpage", eventServices.findAllEventpageByChoir(user.getChoir(),page,size,sort));
      model.addAttribute("fieldList", churchServices.findListofChurchByChurchId(null));
      model.addAttribute("message", message);
      message="";
    return "choirAdmin/event";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value ="/choir/event")
   public String createOrUpdateEvent(@ModelAttribute Event event) throws Exception {
      Event eventdb=eventServices.findById(event.getId());
      Event savedEvent=eventServices.saveOrUpdateEvent(event);
      if(eventdb==null)
       message=savedEvent.getTitle()+" Saved Sucessfully";
       else message=savedEvent.getTitle()+" Updated Sucessfully"; 
    return "redirect:/choir/event";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value ="/choir/event/delete")
   public String deleteEvent(@ModelAttribute Event event) throws Exception {
      Event evt=eventServices.findById(event.getId());
      if(evt==null)
       message="No such Event";
       else {
         announcementServices.deleteAnnouncement(event.getId());
         message=evt.getTitle()+" Updated Sucessfully"; 
       }
    return "redirect:/choir/event";
   }
   // announcement
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/announcement")
   public String getAnnouncementPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="5") int size,@RequestParam(defaultValue ="title") String sort) throws Exception {
      model.addAttribute("Message", message);
      model.addAttribute("totalAnnouncement", announcementServices.findlistofAnnouncementByChoir(user.getChoir()).size());
      model.addAttribute("totalmember",userServices.findAllUserJoinedChoir(user.getChoir()).size());
      model.addAttribute("announcementPage",announcementServices.findAllAnnouncementpageByChoir(user.getChoir(),page,size,sort));
      message="";
    return "choirAdmin/announcement";
   }
   // announcement
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value ="/choir/announcement")
   public String createOrUpdateFinancePage(@ModelAttribute Announcement announcement) throws Exception {
      Announcement ann=announcementServices.findById(announcement.getId());
      Announcement announce=announcementServices.saveOrUpdateAnnouncement(announcement);
      if(ann==null)
       message=announce.getTitle()+" Saved Sucessfully";
       else message=announce.getTitle()+" Updated Sucessfully"; 
    return "redirect:/choir/announcement";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value ="/choir/announcement/delete")
   public String deleteFinance(@ModelAttribute Announcement announcement) throws Exception {
      Announcement ann=announcementServices.findById(announcement.getId());
      if(ann==null)
       message="No such announcement";
       else {
         announcementServices.deleteAnnouncement(ann.getId());
         message=ann.getTitle()+" Updated Sucessfully"; 
       }
    return "redirect:/choir/announcement";
   }
   @Autowired private ContributionServices contributionServices;
   // finance
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/finance")
   public String getContributionPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="startDate") String sort)
   {model.addAttribute("financePage",contributionServices.findAllpageableContributionByChoir(user.getChoir(), page, size, sort));
   model.addAttribute("contributions",contributionServices.findListofContributionByChoir(user.getChoir()));
   model.addAttribute("churchContributions",contributionServices.findListofContributionByChurch(user.getChurch()));
   model.addAttribute("message",message);
   message="";   
   return "choirAdmin/finance";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value="/choir/finance")
   public String createContribution(@ModelAttribute Contribution contribution)
   {Contribution contr=contributionServices.findById(contribution.getId());
      Contribution ctn=contributionServices.saveOrUpdateContribution(contribution);
      if(contr==null)
       message=ctn.getTitle()+" Saved Sucessfully";
       else message=ctn.getTitle()+" Updated Sucessfully"; 
      return "redirect:/choir/finance";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value="/choir/finance/delete")
   public String deleteContribution(@ModelAttribute Contribution contribution)
   {Contribution contr=contributionServices.findById(contribution.getId());
      
      if(contr==null) message=" This contribution does't exist";
       else{contributionServices.deleteContribution(contr.getId()); message=contr.getTitle()+" Deleted Sucessfully"; }
      return "redirect:/choir/finance";
   }
   // offer notification
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/financialoffering")
   public String getFinancialOfferingPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="createDate") String sort) throws Exception {
      model.addAttribute("message", message);
      model.addAttribute("offerspage", offeredContributionServices.findAllpageableOfferedContributions(page, size, sort));
      model.addAttribute("availablecontribution", contributionServices.findListofContributionByChoir(user.getChoir()));
      message="";
    return "choirAdmin/financialoffering";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value="/choir/financialoffering")
   public String createOffer(@ModelAttribute OfferedContribution contribution)
   {OfferedContribution contr=offeredContributionServices.findById(contribution.getId());
      Random random=new Random();
      int i=random.nextInt(10000);
      int j=random.nextInt(10000);
      contribution.setPaymentCode(i+"-"+j);
      OfferedContribution ctn=offeredContributionServices.saveOrUpdateOfferedContribution(contribution);
      if(contr==null)
       message=ctn.getUser().getName()+" Contribution Saved Sucessfully";
       else message=ctn.getUser().getName()+" Contribution Updated Sucessfully"; 
      return "redirect:/choir/financialoffering";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @GetMapping(value="/choir/financialoffering/delete")
   public String deleteOffer(@ModelAttribute OfferedContribution contribution)
   {OfferedContribution contr=offeredContributionServices.findById(contribution.getId());
      if(contr==null) message=" This contribution does't exist";
       else{offeredContributionServices.deleteOfferedContribution(contr.getId()); message=contr.getUser().getName()+" Contribution has Deleted Sucessfully"; }
      return "redirect:/choir/financialoffering";
   }
   // users
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/user")
   public String getUserPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="5") int size,@RequestParam(defaultValue ="name") String sort) throws Exception {
      model.addAttribute("choirMemberPage",userServices.findAllUserJoinedChoirpage(user.getChoir(),page,size,sort));
      model.addAttribute("totalmember",userServices.findAllUserJoinedChoir(user.getChoir()).size());
      List<User> allUsers =userServices.findListOfUserJoinedChurch(user.getChurch());
      List<User> upcomers = allUsers.stream()
                                  .filter(user -> user.getChoir() == null)
                                  .collect(Collectors.toList());
      model.addAttribute("upcomers",upcomers.size());
    return "choirAdmin/user";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/membership")
public String getMemberships(Model  model,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "5") int size,
@RequestParam(defaultValue = "creationDate")String sort) {
   model.addAttribute("Message", message);
   model.addAttribute("membershipList",choirMembershipServices.findlistofChoirMembershipByChoir(user.getChoir()));
   message="";
   return "choirAdmin/membership";
}
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value="/choir/membership/{approval}")
      public String createMemberships(@ModelAttribute ChoirMembership choirmembership,@PathVariable boolean approval) {
         ChoirMembership membership=choirMembershipServices.findById(choirmembership.getId());
         membership.setApprovedByChoirPresident(approval);
         ChoirMembership cm=choirMembershipServices.saveOrUpdateChoirMembership(membership);
         if(approval==true) message=cm.getUser().getName()+"membership has been approved";
         else message=cm.getUser().getName()+"membership has been rejected";
      return "redirect:/choir/membership";}
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @GetMapping(value="/choir/feedback")
   public String getFeedbackPage(Model model){
      model.addAttribute("Message",message);
      message="";
      model.addAttribute("feedbackList",feedbackServices.findAllFeedbackByChoir(user.getChoir()));
    return "choirAdmin/feedback";
   }
   @PreAuthorize("hasRole('ROLE_CHOIR')")
   @PostMapping(value="/choir/feedback")
   public String createFeedback(@ModelAttribute Feedback feedback){
      feedbackServices.saveOrUpdateFeedback(feedback);
      message="Feedback saved successfully";
    return "redirect:/choir/feedback";
   }
    @PreAuthorize("hasRole('ROLE_CHOIR')")
    @PostMapping(value="/choir/feedback/delete")
   public String deleteFeedback(@ModelAttribute Feedback feedback){
      message=feedbackServices.deleteFeedback(feedback);
    return "redirect:/choir/feedback";
   }
   // account
@PreAuthorize("hasRole('ROLE_CHOIR')")
@GetMapping(value="/choir/account")
   public String getAccountPage(Model model) throws Exception {
    return "choirAdmin/account";
   }
   // invitation
   @Autowired EmailSenderServiceConfig sendEmail=new EmailSenderServiceConfig();
   @Autowired private ChoirServices choirServices;
   @PostMapping(value="/choir/invite")
   public String createInvitation(@ModelAttribute Invitation invitation)
   {  Choir choir=choirServices.findChoirById(invitation.getInvitedChoir().getId());
      if(choir!=null)
      {  Church destinationChurch=choir.getChurch();
         Church actuaChurch=user.getChurch();
         boolean areInSameChurch=(actuaChurch.getId()==destinationChurch.getId());
         boolean areInSameDistrict=(actuaChurch.getChurchId().getId()==destinationChurch.getChurchId().getId());
         System.out.println("test are in Same district"+actuaChurch.getChurchId().getId()+"="+destinationChurch.getChurchId().getId());
         boolean areInSameField=(actuaChurch.getChurchId().getChurchId()==destinationChurch.getChurchId().getChurchId());
         System.out.println("test are in Same Field"+actuaChurch.getChurchId().getChurchId().getId()+"="+destinationChurch.getChurchId().getChurchId().getId());
         if((areInSameChurch) ||(areInSameDistrict))
           { invitation.setApprovalTarget("CHURCH");
         //   sendEmail.eventForApprovalNotification(userServices.fin, message, message, message, message, message, message, message);
         }
         else if(!areInSameDistrict&&areInSameField)
          invitation.setApprovalTarget("_DISTRICT");
         else if(!areInSameField)
         {
            invitation.setApprovalTarget("_FIELD");
         }
         Invitation saveInvitation=invitationServices.saveOrUpdateInvitation(invitation);
         message=saveInvitation.getInvitedChoir().getName()+" has been sent successfully";

      }
      else{
         message="Please select a choir for invitation";
      }
      return "redirect:/choir/event";
   }
}
