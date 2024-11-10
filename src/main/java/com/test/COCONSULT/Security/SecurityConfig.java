package com.test.COCONSULT.Security;

import com.test.COCONSULT.JWT.JwtAuthEntryPoint;
import com.test.COCONSULT.JWT.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.Filter;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return jwtAuthEntryPoint;


    }
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
   private CorsConfigurationSource corsConfigurationSource;



    private final String[] PUBLIC_ENDPOINTS={

            "/api/auth/signup/employee",
            "/api/auth/signIn",
            "/api/works/sum_carbo_works",
            "/api/Bilan/list-Bilan",
            "/api/message/add-message",
            "/api/message/list-message",
            "/api/Solution/list-Solution",
            "/api/Solution/add-solution",
            "/api/works/sum_carbo_works",
            "/api/user/validate-user/{idUser}",
            "/api/user/list-user",
            "/api/user/list-Userco2/ASC",
            "/api/user/list-RolesName/{RolesName}",
            "/api/user/validate-user/{iduser}",
            "/api/user/delete-user/{iduser}",
            "/api/user/getUser/{idUser}",
            "/v2/api-docs",
            "/configuration/ui",
            "/-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/swagger-resources",
            "/configuration/security",
            "/v3/api-docs/**",
            "/swagger-ui/**",
             "/refreshToken"    };
    @Override

    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource).and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/refreshToken").permitAll() // Permit access to refreshToken endpoint

                .antMatchers("/**").permitAll()
                .anyRequest().authenticated() // Require authentication for any other endpoint

                .and()
                .httpBasic();
                
                 http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
               .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
    }




   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
   http
           .cors().configurationSource(corsConfigurationSource).and()
           .csrf().disable()
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
           .authorizeRequests()
           .antMatchers("/**").permitAll()
           .anyRequest().authenticated()
           .and()
           .httpBasic();

    }*/
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
                // Public endpoints accessible by anyone
                .antMatchers(HttpMethod.POST, "/api/auth/signup/employee").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/signIn").permitAll()
                .antMatchers(HttpMethod.GET, "/api/works/sum_carbo_works").permitAll()
                // Endpoints accessible only to specific roles
                .antMatchers("/api/Bilan/**").hasAnyAuthority("ROLE_USER", "ADMIN", "Entreprise", "Employee", "Manager", "HR", "CRM", "Consult", "PM")
                .antMatchers("/api/message/**").hasAnyAuthority("ROLE_USER", "ADMIN", "Entreprise", "Employee", "Manager", "HR", "CRM", "Consult", "PM")
                .antMatchers("/api/Solution/**").hasAnyAuthority("ROLE_USER", "ADMIN", "Entreprise", "Employee", "Manager", "HR", "CRM", "Consult", "PM")
                .antMatchers("/api/user/**").hasAnyAuthority("ADMIN", "Entreprise", "Manager", "HR", "CRM", "Consult", "PM")
                // Add more endpoints and roles as needed
                .anyRequest().authenticated()
            .and()
            .httpBasic();
    }*/

    /*
    @Bean
    public HttpFirewall httpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        // Allowing double slashes in URLs
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }*/

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
