package com.example.SchoolClubManagementSystem.Club;

import com.example.SchoolClubManagementSystem.User.User;
import com.example.SchoolClubManagementSystem.User.UserRepository;
import jakarta.validation.Valid;
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
        Club club = new Club();
        club.setTeacher(teacher);
        model.addAttribute("club", club);
        return "club/create";
    }

    @PostMapping("/submit")
    public String submitClub(@Valid Club club, BindingResult bindingResult, @RequestParam("logo") MultipartFile logo, Model model) {
        return clubService.submitClub(club, bindingResult, logo, model);
    }
}