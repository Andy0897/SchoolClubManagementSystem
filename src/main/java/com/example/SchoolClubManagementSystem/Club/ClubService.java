package com.example.SchoolClubManagementSystem.Club;

import com.example.SchoolClubManagementSystem.User.User;
import com.example.SchoolClubManagementSystem.User.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
public class ClubService {
    ClubRepository clubRepository;
    UserRepository userRepository;

    public ClubService(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String submitClub(Club club, BindingResult bindingResult, MultipartFile logo, Model model, Principal principal) {
        boolean nullLogo = false;
        try {
            if (logo.isEmpty()) {
                nullLogo = true;
            }
            else {
                club.setLogo(logo.getBytes());
            }
        } catch(Exception e) {
            model.addAttribute("club", club);
            model.addAttribute("hasUploadError", true);
            model.addAttribute("isLogoSelected", !nullLogo);
            return "club/create";
        }
        System.out.println("is null: " + nullLogo);
        System.out.println(club.getTeacher());
        if (bindingResult.hasFieldErrors("name") || bindingResult.hasFieldErrors("description") || nullLogo) {
            model.addAttribute("club", club);
            model.addAttribute("hasUploadError", false);
            model.addAttribute("isLogoSelected", !nullLogo);
            return "club/create";
        }

        User teacher = userRepository.getUserByUsername(principal.getName());
        club.setTeacher(teacher);

        clubRepository.save(club);
        return "redirect:/home";
    }

    public String submitJoinClub(Long clubId, Principal principal) {
        Club club = clubRepository.findById(clubId).get();
        User user = userRepository.getUserByUsername(principal.getName());
        club.addStudent(user);
        clubRepository.save(club);
        return "redirect:/clubs/" + clubId;
    }

    public boolean checkIfUserIsInClub(User student, Club club) {
        List<User> students = club.getStudents();
        for(User st : students) {
            if(st.getId() == student.getId()) {
                return true;
            }
        }
        return false;
    }
}