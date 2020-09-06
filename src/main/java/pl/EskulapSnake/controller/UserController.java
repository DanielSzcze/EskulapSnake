package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.EskulapSnake.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userServise;

    @Autowired
    public UserController(UserService userServise) {
        this.userServise = userServise;
    }


}
