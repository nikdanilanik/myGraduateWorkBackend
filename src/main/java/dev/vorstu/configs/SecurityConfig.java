package dev.vorstu.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/authAndRegistr/**").permitAll()

//                .antMatchers(HttpMethod.PUT,"/api/base/students/updateForStudents").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name())

                // По хорошему, нужно было сделать так:
                // .antMatchers(HttpMethod.GET, "/api/base/students/getData").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/base/students").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/base/students/{id}").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/base/students/{fio}").authenticated()
//                .antMatchers(HttpMethod.GET, "/api/base/groups/{id}").authenticated()

//                .antMatchers("/api/base/students/**").hasAuthority(Role.ADMIN.name())
                .antMatchers("/api/base/**").authenticated()
                .antMatchers("/api/chatController/**").authenticated()
//                .antMatchers("/api/base/students/fio/**").permitAll()

                .anyRequest()
                .authenticated()
            .and()
                .httpBasic()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) throws IOException, ServletException {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                })
                .and()
                .csrf().disable()
                .cors().disable();

        return http.build();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery(
                        "select login, p.password as password, enable "
                                + "from users_access as u "
                                + "inner join passwords as p on u.passwords_id = p.id "
                                + "where login=?")
                .authoritiesByUsernameQuery("select login, role, user_id from users_access where login=?");

    }
}
