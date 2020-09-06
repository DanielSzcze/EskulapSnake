package pl.EskulapSnake.service;

import org.springframework.stereotype.Service;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new RuntimeException("in db is no user with this username"));
    }
}
