package sda.choir.sdaController;

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
import sda.choir.Model.ChoirMembership;
import sda.choir.Model.Church;
import sda.choir.Model.OfferedContribution;
import sda.choir.Model.User;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirMembershipServices;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.OfferedContributionServices;
// import sda.choir.Services.UserServices;
@Controller
@SessionAttributes("user")
public class DefaultUserController {
    // @Autowired private UserServices userServices;
    @Autowired private EventServices eventServices;
    @Autowired private AnnouncementServices announcementServices;
    public static String message="";
    public static User user=new User();
    // home
    @PreAuthorize("hasRole('ROLE_USER')")  
    @GetMapping(value="/user/home")
    public String getDefaultPage(Model model) throws Exception {
        model.addAttribute("user", user);
        Church church=churchServices.findById(user.getChurch().getId());
        model.addAttribute("choirList", church.getChoirList());
        return "index/index";
    }
    // event
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/user/event")
    public String getDefaultEventPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
        model.addAttribute("eventpage", eventServices.findAllEventpage(page, size, sort));
        return "index/event";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/user/announcement")
    public String getDefaultAnnouncementPage(Model model,@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="6") int size,@RequestParam(defaultValue ="startDate") String sort) throws Exception {
        model.addAttribute("announcementpage", announcementServices.findAllAnnouncementpageByChurch(user.getChurch(), page, size, sort));
        return "index/announcement";
    }
    @Autowired private ChurchServices churchServices;
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/user/choir")
    public String getDefaultChoirPage(Model model) throws Exception {
       Church church=churchServices.findById(user.getChurch().getId());
        model.addAttribute("choirList", church.getChoirList());
        return "index/choir";
    }
    @Autowired ChoirMembershipServices choirMembershipServices;
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/user/membership")
    public String getDefaultMembershipPage(Model model) throws Exception {
        Church church=churchServices.findById(user.getChurch().getId());
        model.addAttribute("message", message);
        model.addAttribute("choirList", church.getChoirList());
        model.addAttribute("membershipList",choirMembershipServices.findUserMembership(user));
        message="";
        return "index/membership";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(value="/user/membership")
    public String createMembershipPage(@ModelAttribute ChoirMembership choir) throws Exception {
        ChoirMembership member=choirMembershipServices.findUserMembership(user);
        if(member==null)
        {
          ChoirMembership cr=choirMembershipServices.saveOrUpdateChoirMembership(choir);
            message=cr.getUser().getName()+ "your Membership added successfully";
        }
        else{
            choir.setId(member.getId());
            ChoirMembership mb=choirMembershipServices.saveOrUpdateChoirMembership(choir);
            message=mb.getUser().getName()+" your Membership Updated successfully";
        }
      
        return "redirect:/user/membership";
    }
    // contribution
     @Autowired OfferedContributionServices offeredContributionServices;
    @Autowired ContributionServices contributionServices;
    @GetMapping(value = "/user/contribution")
    public String authorizedFinancialOfferwPage(Model model,@RequestParam(defaultValue = "0")int pageNumber,
    @RequestParam(defaultValue = "6") int pagesize,@RequestParam(defaultValue ="id")String sortname){
        model.addAttribute("message", message);
        message="";
        model.addAttribute("Finacialoffering", new OfferedContribution());
        model.addAttribute("contributionList",contributionServices.findAllContributions());
        model.addAttribute("ffPage",offeredContributionServices.findAllOfferedContributionByUser(user,pageNumber, pagesize, "createDate"));
        return "index/contribution";
    }
    
     @PostMapping(value = "/user/contribution")
    public String createFinancialOfferwPage(@ModelAttribute OfferedContribution offer){
       try {
        Random rnd=new Random();
        int r1=rnd.nextInt(10000);
        int r2=rnd.nextInt(10000);
        offer.setPaymentCode(r1+"-"+r2);
       OfferedContribution offers= offeredContributionServices.saveOrUpdateOfferedContribution(offer);
       message=offers.getUser().getName()+" offer has been added successfully";
       } catch (Exception e) {
       message=e.getMessage();
       }
        return "redirect:/user/contribution";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/user/account")
    public String getDefaultAcountPage(Model model) throws Exception {;
        return "index/account";
    }
}
