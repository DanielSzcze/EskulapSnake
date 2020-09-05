package pl.EskulapSnake.service;
import org.springframework.stereotype.Service;
import pl.EskulapSnake.repository.UserRepository;

@Service
public class UserServise {
    private UserRepository userRepository;
    public UserServise(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
