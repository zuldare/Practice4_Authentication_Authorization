package es.codeurjc.books.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private CustomAuthenticationProvider authProvider;

    public SecurityConfiguration(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Public access to swagger-ui
        http.authorizeRequests()
                .antMatchers("/swagger-ui").permitAll();


        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/*").hasAnyRole("USER", "ADMIN");


        http.csrf().disable();
    }
}