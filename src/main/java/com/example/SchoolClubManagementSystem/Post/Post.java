package com.example.SchoolClubManagementSystem.Post;

import com.example.SchoolClubManagementSystem.Club.Club;
import com.example.SchoolClubManagementSystem.ImageEncoder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Column(name = "post_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String description;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    private LocalDate uploadDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return new ImageEncoder().encodeToBase64(image);
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}