package com.nagiel.tpspring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagiel.tpspring.model.User;
import com.nagiel.tpspring.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
    private UserService userService;	
    
    @Autowired
    PasswordEncoder encoder;
    
    /**
    * Read - Get all users
    * @return - An Iterable object of User full filled
    */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }
	
	/**
	 * Read - Get one User 
	 * @param id The id of the User
	 * @return An User object full filled
	 */
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable("id") final Long id) {
		Optional<User> User = userService.getUser(id);
		if(User.isPresent()) {
			return User.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Update - Update an existing User
	 * @param id - The id of the User to update
	 * @param User - The User object updated
	 * @return
	 */
	@PutMapping("/user/{id}")
	public User updateUser(@PathVariable("id") final Long id, @RequestBody User user) {
		Optional<User> e = userService.getUser(id);
		if(e.isPresent()) {
			User currentUser = e.get();
			
			String username = user.getUsername();
			if(username != null) {
				currentUser.setUsername(username);
			}
			String email = user.getEmail();
			if(email != null) {
				currentUser.setEmail(email);
			}
			String password = user.getPassword();
			if(password != null) {
				currentUser.setPassword(encoder.encode(password));
			}
			userService.saveUser(currentUser);
			return currentUser;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Delete - Delete an User
	 * @param id - The id of the User to delete
	 */
	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@PathVariable("id") final Long id) {
		userService.deleteUser(id);
	}
}
