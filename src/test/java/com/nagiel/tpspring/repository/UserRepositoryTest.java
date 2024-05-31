package com.nagiel.tpspring.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.nagiel.tpspring.model.User;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
    private UserRepository userRepository;	

    @Test
    public void testFindByUsername() {
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("john_doe");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("john_doe");
    }
    
    @Test
    public void testExistsByUsername() {
        User user = new User();
        user.setUsername("alice_smith");
        user.setEmail("alice.smith@example.com");
        user.setPassword("password");
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("alice_smith");
        assertThat(exists).isTrue();
    }

    @Test
    public void testExistsByEmail() {
        User user = new User();
        user.setUsername("jane_doe");
        user.setEmail("jane.doe@example.com");
        user.setPassword("password");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("jane.doe@example.com");
        assertThat(exists).isTrue();
    }
}
