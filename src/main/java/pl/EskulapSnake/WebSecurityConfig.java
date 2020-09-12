package pl.EskulapSnake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    DataSource dataSource;
    String userQuery = "SELECT username, password, enabled FROM users WHERE username=?";

    String authoritiesQuery = "SELECT username, r.name FROM users u\n" +
            "JOIN users_roles ur ON u.id=ur.user_id \n" +
            "JOIN roles r ON ur.role_id=r.id\n" +
            "WHERE username=? ;";

    @Autowired
    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authoritiesQuery);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/" ,"/login","/login/error", "/logout").permitAll()
                .antMatchers("/auth/**").anonymous()
                .antMatchers("/database").hasRole("ADMIN")
                .antMatchers("/entries/**", "/roles/**", "/visits/**").hasAnyRole("ADMIN", "RECORDER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/process_login")
                .failureUrl("/login/error")
//                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
//                    httpServletResponse.sendRedirect("/login/error");
//                })
                .and()
                .logout().logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
