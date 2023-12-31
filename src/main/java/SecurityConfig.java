// Day 8

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig {
    @Bean
    UserDetailsService authentication() {
        UserDetails peter = User.builder()
                .username("peter")
                .password("ppassword")
                .roles("USER")
                .build();

        UserDetails jodie = User.builder()
                .username("jodie")
                .password("jpassword")
                .roles("USER", "ADMIN")
                .build();

        System.out.println("   >>> Peter's password: "+peter.getPassword());
        System.out.println("   >>> Jodie's password: "+jodie.getPassword());

        return new InMemoryUserDetailsManager(peter, jodie);
    }
}
