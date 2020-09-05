package pl.EskulapSnake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.EskulapSnake.service.UserServise;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserServise userServise;

    @Autowired
    public UserController(UserServise userServise) {
        this.userServise = userServise;
    }


}
