package com.example.SchoolClubManagementSystem.Club;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ClubService {
    ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Transactional
    public String submitClub(Club club, BindingResult bindingResult, MultipartFile logo, Model model) {
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
            return "club/add";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("club", club);
            return "/add";
        }

        clubRepository.save(club);
        return "redirect:/home";
    }
}