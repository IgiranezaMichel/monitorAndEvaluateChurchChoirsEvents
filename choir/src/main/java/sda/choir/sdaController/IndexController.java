package sda.choir.sdaController;

import java.util.Collection;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sda.choir.Model.User;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.UserServices;
import sda.choir.sdaRepository.UserRepository;
import sda.choir.securityConfiguration.EmailSenderServiceConfig;

@Controller
public class IndexController {
    @Autowired UserServices userServices;
    @Autowired ChurchServices churchServices;
    @Autowired private EmailSenderServiceConfig sendEmail=new EmailSenderServiceConfig();
    public static User user=new User();
    public static String message = "";
    @GetMapping(value="/")
    public String getPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("message", message);
        model.addAttribute("listOfChurch", churchServices.findListofChurchByType("CHURCH"));
        if(!message.isEmpty())
        {message="";}
        return "index";
    }
    @RequestMapping(value="/login")
    public String getLoginPage(Model model) {
        return "login";
    }
    @Autowired private UserRepository userRepository;
    @GetMapping(value="/defaultsuccesslogin")
    public String getDefaultLoginPage() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       String username=auth.getName();
        User authuser=userRepository.findByUsername(username);
    if(auth!=null && auth.isAuthenticated())
    {
      Collection<? extends GrantedAuthority> authority=auth.getAuthorities();
      if(authority.contains(new SimpleGrantedAuthority("ROLE_USER")))
      { DefaultUserController.user=authuser;
        return "redirect:/user/home";
      }else if(authority.contains(new SimpleGrantedAuthority("ROLE_SINGER"))){
        //  adminController.user=authuser;
        return "redirect:/singer/home";
      }
      else if(authority.contains(new SimpleGrantedAuthority("ROLE_CHOIR"))){
         ChoirAdminController.user=authuser;
        return "redirect:/choir/home";
      }
      else if(authority.contains(new SimpleGrantedAuthority("ROLE_CHURCH"))){
         ChurchAdminController.user=authuser;
        return "redirect:/church/home";
      }
       else if(authority.contains(new SimpleGrantedAuthority("ROLE_DISTRICT"))){
         PastorController.user=authuser;
        return "redirect:/pastor/home";
      }
         else if(authority.contains(new SimpleGrantedAuthority("ROLE_FIELD"))){
         sefieldController.user=authuser;
        return "redirect:/field/home";
      }
       else if(authority.contains(new SimpleGrantedAuthority("ROLE_UNION"))){
         seunionController.user=authuser;
        return "redirect:/union/home";
      }
    }
        return "/login";
    }
    // logout

    @GetMapping(value = "/logout-success")
    public String defaultLogoutPage(Model model){
        return "login";
    }
    @PostMapping(value = "/")
    public String createAccount(@ModelAttribute User user,Model model) {
       try {
         user.setRole("ROLE_USER");
         user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
         User usr=userServices.saveOrUpdateUser(user);
         sendEmail.sendSignUpEmail(usr.getEmail(),"Sda signup", usr.getName());
         message=user.getName()+" Your account has been created Successfully";
       } catch (Exception e) {
        message=e.getMessage()+" Account already exists";
       }
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "redirect:/";
    }
}
