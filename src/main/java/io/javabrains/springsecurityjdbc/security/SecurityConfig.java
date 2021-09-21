package io.javabrains.springsecurityjdbc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity  // this annotation tells Spring Security to give us AuthenticationManagerBuilder object
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication() // Configure a data source Bean to tell how to access the JDBC database
        // Spring Boot knows it should configure the H2 DB as the data source here.
        // Thanks Spring Boot! No need to create a Bean for the data source.
        // this data source could point to any database we have set for this Bean
                .dataSource(this.dataSource)
        // --> If we give Spring Security a clean DB, it will create a User table and the authority table for us
        // now we have our H2 DB populated with a couple of tables: User, Authorities
                .withDefaultSchema()
        // We can have a bunch of users created for that datasource.
                .withUser(User.withUsername("user").password("pass").roles("USER"))
                .withUser(User.withUsername("admin").password("pass").roles("ADMIN"));
    }

    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }

    @Bean  // a fake pw encoder (clear text)
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}

