package es.codeurjc.books.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static es.codeurjc.books.config.Constants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityA extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .sessionManagement()
                // COOKIES DEACTIVATED
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // CORS default values activated
                .cors().and()
                // CSRF deactivated
                .csrf().disable()
                // Login does not need authentication
                .authorizeRequests()

                // ALLOW CERTAIN OPERATIONS TO NON-AUTHORIZED USERS
                .antMatchers(HttpMethod.POST, LOGIN_URL, SIGNUP_URL).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN_URL, BOOKS_URL).permitAll()

                // ALLOW ACCES TO DOCUMENTATION OF THE API
                .antMatchers(API_DOCS_URL, API_DOCS_YAML, SWAGGER_UI_HTML, SWAGGER_UI_URL).permitAll()

                // Any other request must be authenticated using JWT
                .anyRequest().authenticated().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
