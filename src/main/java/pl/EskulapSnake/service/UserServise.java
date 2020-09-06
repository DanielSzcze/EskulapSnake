package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.repository.UserRepository;

@Service
public class UserServise {
    private UserRepository userRepository;
    public UserServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUserName(String username) {
        return userRepository.findUserByUserName(username).orElseThrow(() -> new RuntimeException("in db is no user with this username"));
    }
}
