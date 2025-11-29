package com.example.SchoolClubManagementSystem.Post;

import com.example.SchoolClubManagementSystem.Club.Club;
import com.example.SchoolClubManagementSystem.Club.ClubRepository;
import com.example.SchoolClubManagementSystem.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/posts")
public class PostController {
    ClubRepository clubRepository;
    UserRepository userRepository;
    PostService postService;

    public PostController(ClubRepository clubRepository, UserRepository userRepository, PostService postService) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
        this.postService = postService;
    }

    @GetMapping("/add")
    public String getAddPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("hasUploadError", false);
        return "post/add";
    }

    @PostMapping("/submit")
    public String getSubmitPost(@Valid Post post, BindingResult bindingResult, @RequestParam("image") MultipartFile image, Principal principal, Model model) {
        return postService.submitPost(post, bindingResult, image, principal, model);
    }
}