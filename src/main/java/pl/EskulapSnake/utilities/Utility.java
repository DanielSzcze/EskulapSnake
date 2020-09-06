package pl.EskulapSnake.utilities;

import org.springframework.security.core.Authentication;
import pl.EskulapSnake.model.Employee;
import pl.EskulapSnake.model.User;
import pl.EskulapSnake.service.EmployeeService;
import pl.EskulapSnake.service.UserServise;

public class Utility {

    public static User findLoggedUser(Authentication authentication, UserServise userServise) {
        final String username = authentication.getName();
        final User loggedUsername = userServise.findByUserName(username);
        return loggedUsername;

    }

    public  static Employee findloggedEmployee(Authentication authentication,
                                               UserServise userServise, EmployeeService employeeService){
        User loggedUser = findLoggedUser(authentication, userServise);
        Employee loggedEmployee =employeeService.findbyUser(loggedUser);
    }
}
