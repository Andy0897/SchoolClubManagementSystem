package com.example.SchoolClubManagementSystem.Club;

import com.example.SchoolClubManagementSystem.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

public class Club {
    @Column(name = "club_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String title;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String description;

    @OneToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private User teacher;

    @ElementCollection
    @Lob
    private byte[] logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}