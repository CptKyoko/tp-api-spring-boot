package com.nagiel.tpspring.controller;

import java.util.Optional;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagiel.tpspring.model.Article;
import com.nagiel.tpspring.model.User;
import com.nagiel.tpspring.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
    private ArticleService articleService;
	
    /**
    * Read - Get all articles
    * @return - An Iterable object of Article full filled
    */
	@CrossOrigin(origins = "http://0.0.0.0:8100")
    @GetMapping("/articles")
    public Iterable<Article> getArticles() {
        return articleService.getArticles();
    }
    
	/**
	 * Create - Add a new Article
	 * @param Article An object Article
	 * @return The Article object saved
	 */
	@PostMapping("/article")
	public Article createArticle(@RequestBody Article Article) {
		return articleService.saveArticle(Article);
	}
	
	/**
	 * Read - Get one Article 
	 * @param id The id of the Article
	 * @return An Article object full filled
	 */
	@GetMapping("/article/{id}")
	public Article getArticle(@PathVariable("id") final Long id) {
		Optional<Article> Article = articleService.getArticle(id);
		if(Article.isPresent()) {
			return Article.get();
		} else {
			return null;
		}
	}

	/**
	 * Read - Get all Articles by User 
	 * @param id The id of the User
	 * @return An list of Articles object by user
	 */
	@GetMapping("/articles/{id}")
	public List<Article> getArticlesByUser(@PathVariable("id") final Long id) {
		return articleService.getArticlesByUser(id);
	}
	
	/**
	 * Update - Update an existing Article
	 * @param id - The id of the Article to update
	 * @param Article - The Article object updated
	 * @return
	 */
	@PutMapping("/article/{id}")
	public Article updateArticle(@PathVariable("id") final Long id, @RequestBody Article article) {
		Optional<Article> e = articleService.getArticle(id);
		if(e.isPresent()) {
			Article currentArticle = e.get();
			
			String title = article.getTitle();
			if(title != null) {
				currentArticle.setTitle(title);
			}
			String content = article.getContent();
			if(content != null) {
				currentArticle.setContent(content);
			}
			User user = article.getUser();
			if(user != null) {
				currentArticle.setUser(user);
			}
			Date date = article.getDate();
			if(date != null) {
				currentArticle.setDate(date);
			}
			articleService.saveArticle(currentArticle);
			return currentArticle;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Delete - Delete an Article
	 * @param id - The id of the Article to delete
	 */
	@DeleteMapping("/article/{id}")
	public void deleteArticle(@PathVariable("id") final Long id) {
		articleService.deleteArticle(id);
	}
}

