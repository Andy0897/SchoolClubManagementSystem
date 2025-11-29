package com.example.SchoolClubManagementSystem.Post;

import com.example.SchoolClubManagementSystem.Club.Club;
import com.example.SchoolClubManagementSystem.Club.ClubRepository;
import com.example.SchoolClubManagementSystem.User.User;
import com.example.SchoolClubManagementSystem.User.UserRepository;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class PostService {
    UserRepository userRepository;
    ClubRepository clubRepository;
    PostRepository postRepository;

    public PostService(UserRepository userRepository, ClubRepository clubRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.clubRepository = clubRepository;
        this.postRepository = postRepository;
    }

    public String submitPost(Post post, BindingResult bindingResult, MultipartFile image, Principal principal, Model model) {
        User user = userRepository.getUserByUsername(principal.getName());
        Club club = clubRepository.findById(clubRepository.getClubIdByTeacherId(user.getId())).get();
        try {
            if (!image.isEmpty()) {
                post.setImage(image.getBytes());
            }
            else {
                post.setImage(null);
            }
        } catch(Exception e) {
            System.out.println("Exception error");
            model.addAttribute("post", post);
            model.addAttribute("hasUploadError", true);
            return "post/add";
        }

        if(bindingResult.hasFieldErrors("description")) {
            System.out.println("Binding Result error");
            model.addAttribute("post", post);
            model.addAttribute("hasUploadError", false);
            return "post/add";
        }
        post.setUploadDate(LocalDate.now());
        club.addPost(post);
        postRepository.save(post);
        clubRepository.save(club);
        return "redirect:/clubs/" + club.getId();
    }
}