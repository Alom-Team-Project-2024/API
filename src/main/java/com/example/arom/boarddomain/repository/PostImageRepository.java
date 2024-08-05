package com.example.arom.boarddomain.repository;

import com.example.arom.boarddomain.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
