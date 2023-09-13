package sda.choir.sdaController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sda.choir.Model.Choir;
import sda.choir.Model.Church;
import sda.choir.Services.AnnouncementServices;
import sda.choir.Services.ChoirMembershipServices;
import sda.choir.Services.ChoirServices;
import sda.choir.Services.ChurchServices;
import sda.choir.Services.ContributionServices;
import sda.choir.Services.EventServices;
import sda.choir.Services.InvitationServices;
import sda.choir.Services.OfferedContributionServices;
import sda.choir.Services.UserServices;

@RestController
public class Rest_Api {
   @Autowired private AnnouncementServices announcementServices;
   @Autowired private ChoirMembershipServices choirMembershipServices;
   @Autowired private ChoirServices choirServices;
   @Autowired private ChurchServices churchServices;
   @Autowired private ContributionServices contributionServices;
   @Autowired private EventServices eventServices;
   @Autowired private InvitationServices invitationServices;
   @Autowired private OfferedContributionServices offeredContributionServices;
   @Autowired private UserServices userServices;
   @GetMapping(value="/church/{churchType}")
   public List<Church>getSelectedChurch(@PathVariable String churchType)
   {
      return churchServices.findListofChurchByType(churchType);
   }
   @GetMapping(value = "/api/church/{churchId}")
   public List<Church>getListOfChurchByChurchId(@PathVariable int churchId)
   { Church church=churchServices.findById(churchId);
      return churchServices.findListofChurchByChurchId(church);
   }
   @GetMapping(value = "/api/choir/{church}")
   public List<Choir>getListOfChoirByChurchId(@PathVariable int church)
   { Church rschurch=churchServices.findById(church);
      return choirServices.findListofChoirByChurch(rschurch);
   }
}
