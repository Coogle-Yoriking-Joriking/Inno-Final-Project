package com.inno.coogle.repository;

import com.inno.coogle.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    @Query(value = "FROM Post  WHERE UPPER(ingredientsList)  LIKE CONCAT ('%', UPPER(?1) ,'%') ")
    List<Post> findByIngredientsListContainingIgnoreCase(String keyword);
}
