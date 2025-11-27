package com.example.SchoolClubManagementSystem.Club;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends CrudRepository<Club, Long> {
    @Query(nativeQuery=true, value="SELECT c.club_id FROM `clubs` AS c " +
            "JOIN users ON users.user_id = c.teacher_id " +
            "WHERE users.user_id = :userId;")
    public Long getClubIdByTeacherId(@Param("userId") Long userId);
}