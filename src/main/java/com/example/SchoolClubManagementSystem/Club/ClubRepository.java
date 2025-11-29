package com.example.SchoolClubManagementSystem.Club;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends CrudRepository<Club, Long> {
    @Query(nativeQuery=true, value="SELECT c.club_id FROM `clubs` AS c " +
            "JOIN users ON users.user_id = c.teacher_id " +
            "WHERE users.user_id = :teacherId;")
    public Long getClubIdByTeacherId(@Param("teacherId") Long teacherId);

    @Query(nativeQuery=true, value = "SELECT c.club_id FROM `clubs` AS c " +
            "JOIN clubs_students AS cs ON c.club_id = cs.club_club_id " +
            "WHERE cs.students_user_id = :studentId;")
    public Long getClubIdByStudentId(@Param("studentId") Long studentId);
}