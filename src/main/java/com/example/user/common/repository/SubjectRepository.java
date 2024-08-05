package com.example.user.common.repository;

import com.example.user.common.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE s.subject LIKE %:keyword%")
    List<Subject> findByValueContaining(@Param("keyword") String keyword);
}
