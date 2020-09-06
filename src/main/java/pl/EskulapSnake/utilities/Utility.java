package pl.EskulapSnake.utilities;

import org.springframework.security.core.Authentication;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.service.EmployeeService;
import pl.EskulapSnake.service.UserService;

public class Utility {

    public static User findLoggedUser(Authentication authentication, UserService userService) {
        final String username = authentication.getName();
        final User loggedUsername = userService.findByUserName(username);
        return loggedUsername;

    }

    public  static Employee findloggedEmployee(Authentication authentication,
                                               UserService userService, EmployeeService employeeService){
        User loggedUser = findLoggedUser(authentication, userService);
        Employee loggedEmployee =employeeService.findByUser(loggedUser);
        return null;
    }
}
