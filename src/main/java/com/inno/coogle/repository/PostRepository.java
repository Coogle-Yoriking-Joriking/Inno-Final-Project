package com.inno.coogle.repository;

import com.inno.coogle.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    //레시피 재료로 검색
    @Query(value = "FROM Post  WHERE UPPER(ingredientsList)  LIKE CONCAT (UPPER(?1) ,'%') ")
    List<Post> findByIngredientsListContainingIgnoreCase(String keyword);

    //레시피 재료와 재외목록을 함께 검색하기
    @Query(value = "FROM Post  WHERE ingredientsList NOT LIKE CONCAT (?1 ,'%') AND ingredientsList  LIKE CONCAT ('%' , ?2 ,'%') " )
    List<Post> filterSearch(String except, String keyword);


}
