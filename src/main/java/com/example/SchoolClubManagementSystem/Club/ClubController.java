package com.example.SchoolClubManagementSystem.Club;

import com.example.SchoolClubManagementSystem.ImageEncoder;
import com.example.SchoolClubManagementSystem.User.User;
import com.example.SchoolClubManagementSystem.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/clubs")
public class ClubController {
    ClubService clubService;
    ClubRepository clubRepository;
    UserRepository userRepository;

    public ClubController(ClubService clubService, ClubRepository clubRepository, UserRepository userRepository) {
        this.clubService = clubService;
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public String getCreateClub(Model model, Principal principal) {
        User teacher = userRepository.getUserByUsername(principal.getName());
        Long clubId = clubRepository.getClubIdByTeacherId(teacher.getId());
        if (clubId == null) {
            Club club = new Club();
            model.addAttribute("club", club);
            model.addAttribute("isLogoSelected", true);
            model.addAttribute("hasUploadError", false);
            return "club/create";
        }
        return "redirect:/access-denied";
    }

    @GetMapping("/my-club")
    public String getMyClub(Principal principal, Model model) {
        User teacher = userRepository.getUserByUsername(principal.getName());
        Long clubId = clubRepository.getClubIdByTeacherId(teacher.getId());
        if (clubId == null) {
            return "redirect:/clubs/create";
        }
        Club club = clubRepository.findById(clubId).get();
        model.addAttribute("club", club);
        model.addAttribute("isMine", true);
        model.addAttribute("isStudent", false);
        model.addAttribute("isJoin", false);
        return "club/show";
    }

    @GetMapping
    public String getAllClubs(Model model) {
        model.addAttribute("clubs", clubRepository.findAll());
        return "club/show-all";
    }

    @GetMapping("/{id}")
    public String getClub(@PathVariable("id") Long id, Principal principal, Model model) {
        User user = userRepository.getUserByUsername(principal.getName());
        Long clubId = clubRepository.getClubIdByTeacherId(user.getId());
        if (clubId != null) {
            return "redirect:/clubs/my-club";
        }
        Club club = clubRepository.findById(id).get();
        model.addAttribute("club", club);
        model.addAttribute("isMine", false);
        model.addAttribute("isStudent", user.getRole().equals("USER"));
        model.addAttribute("isJoin", clubService.checkIfUserIsInClub(user, club));
        model.addAttribute("encoder", new ImageEncoder());
        return "club/show";
    }

    @PostMapping("/submit")
    public String submitClub(@Valid Club club, BindingResult bindingResult, @RequestParam("logo") MultipartFile logo, Model model, Principal principal) {
        return clubService.submitClub(club, bindingResult, logo, model, principal);
    }

    @PostMapping("/submit-join-club/{clubId}")
    public String getSubmitJoinClub(@PathVariable("clubId") Long clubId, Principal principal) {
        return clubService.submitJoinClub(clubId, principal);
    }
}