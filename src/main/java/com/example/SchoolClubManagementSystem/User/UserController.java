package com.example.SchoolClubManagementSystem.User;

import com.example.SchoolClubManagementSystem.Club.Club;
import com.example.SchoolClubManagementSystem.Club.ClubRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    UserService userService;
    UserRepository userRepository;
    ClubRepository clubRepository;

    public UserController(UserService userService, UserRepository userRepository, ClubRepository clubRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
    }

    @GetMapping({"/", "/home"})
    public String getHome(Model model) {
        model.addAttribute("clubs", clubRepository.findAll());
        return "home";
    }

    @GetMapping("/sign-in")
    public String getSignIn(Principal principal) {
        if (principal != null) {
            return "redirect:/access-denied";
        }
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "sign-up";
    }

    @PostMapping("/submit")
    public String submitUser(@Valid User user, BindingResult bindingResult, Model model) {
        return userService.submitUser(user, bindingResult, model);
    }

    @ResponseBody
    @GetMapping("/teacher-has-club")
    public boolean checkIfTeacherHasClub(Principal principal) {
        User teacher = userRepository.getUserByUsername(principal.getName());
        Long clubId = clubRepository.getClubIdByTeacherId(teacher.getId());
        Club club = clubId == null ? null : clubRepository.findById(clubId).get();
        return club != null;
    }

    @GetMapping("/logout")
    public String getLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }
}