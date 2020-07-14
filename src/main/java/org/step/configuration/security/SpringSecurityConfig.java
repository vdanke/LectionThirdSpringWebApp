package org.step.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.step.configuration.security.filter.AuthorizationFilter;
import org.step.configuration.security.filter.LoginUsernamePasswordAuthenticationFilter;
import org.step.configuration.security.token.TokenProvider;
import org.step.configuration.security.token.TokenProviderImpl;
import org.step.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@ComponentScan(basePackages = "org.step")
@PropertySources({
        @PropertySource("classpath:security.properties")
})
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private Environment environment;
    private UserService userService;
    private TokenProvider tokenProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("root")
//                .password("root")
//                .roles("USER");
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        /courses/*
         */
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/courses/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/auth/registration")
                .permitAll()
//                .antMatchers(HttpMethod.GET, "/users")
//                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
        http
                .csrf()
                .disable();
        http
                .formLogin()
                .disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilter(loginUsernamePasswordAuthenticationFilter())
                .addFilter(authorizationFilter());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginUsernamePasswordAuthenticationFilter loginUsernamePasswordAuthenticationFilter() throws Exception {
        LoginUsernamePasswordAuthenticationFilter filter = new LoginUsernamePasswordAuthenticationFilter(authenticationManager());

        filter.setFilterProcessesUrl("/login");

        return filter;
    }

    @Bean
    public AuthorizationFilter authorizationFilter() throws Exception {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(authenticationManager(), environment);

        authorizationFilter.setTokenProvider(tokenProvider);
        authorizationFilter.setUserService(userService);

        return authorizationFilter;
    }

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProviderImpl(environment);
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
}
