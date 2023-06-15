package com.exam.Catering.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //naudojama su @PreAuthorize anotacijom
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @CrossOrigin(origins = "http://localhost:3000") // Replace with your frontend URL
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/*.json", "/client/**", "/swagger-ui.html", "/swagger-ui/**").permitAll() //dalykai, kuriuos mato visi nepriklausomai nuo user role
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/**").hasAuthority(ApplicationUserPermission.ADMIN_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.CLIENT.name())

//                .antMatchers("/success").permitAll() // Allow access to /success URL
                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin()
                .loginProcessingUrl("/login") // Specify the login endpoint URL without the ?error query parameter
                .defaultSuccessUrl("/success") // Redirect to this URL on successful login
                .failureUrl("/login?error") // Redirect to this URL on login failure
                .permitAll();

    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails johnUser = User.builder()
                .username("John")
                .password(passwordEncoder.encode("password"))
//                .roles(ApplicationUserRole.CLIENT.name()) //ROLE_CLIENT
                .authorities(ApplicationUserRole.CLIENT.getGrantedAuthorities())
                .build();

        UserDetails lindaUser = User.builder()
                .username("Linda")
                .password(passwordEncoder.encode("password123"))
//                .roles(ApplicationUserRole.ADMIN.name()) //ROLE_ADMIN
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                johnUser,
                lindaUser
        );
    }
}
