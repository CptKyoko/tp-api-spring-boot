package com.nagiel.tpspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nagiel.tpspring.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>  {
	@EntityGraph(attributePaths = {"user"})
	List<Article> findByUserId(Long userId);
	
	List<Article> findAll();
}

