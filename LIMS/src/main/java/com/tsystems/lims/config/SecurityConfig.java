package com.tsystems.lims.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages = "com.tsystems.lims.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
                handle(httpServletRequest, httpServletResponse, authentication);
                clearAuthenticationAttributes(httpServletRequest);
            }

            protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                if (!response.isCommitted()) {
                    redirectStrategy.sendRedirect(request, response, determineTargetUrl(authentication));
                }
            }

            protected String determineTargetUrl(Authentication authentication) {
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                        return "/schedule";
                    } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                        return "/patients";
                    }
                }
                return "/";
            }

            protected void clearAuthenticationAttributes(HttpServletRequest request) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                }
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:9090"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers("/", "/resources/*/*", "/login", "/login/process", "/rest/eventsByDay").permitAll().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .successHandler(myAuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        getRedirectStrategy().sendRedirect(request, response, "/login?error=" + exception.getMessage());
                    }
                })
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);
    }


}
