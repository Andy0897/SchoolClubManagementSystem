package com.example.SchoolClubManagementSystem.Club;

import com.example.SchoolClubManagementSystem.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {
    @Column(name = "club_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String name;

    @NotEmpty(message = "Полето не може да бъде празно")
    private String description;

    @OneToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @OneToMany
    private List<User> students;

    @Lob
    @Column(name = "logo", columnDefinition = "LONGBLOB")
    private byte[] logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public void addStudent(User user) {
        if(user.getRole().equals("USER")) {
            students.add(user);
        }
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}