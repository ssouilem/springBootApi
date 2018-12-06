package com.services.direct.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_AUTORITY_ADMIN = "hasAuthority('ROLE_ADMIN')";
    private static final String SECURED_PATTERN = "/customers/**";
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //http.requestMatchers()
    		// .antMatchers(SECURED_PATTERN).and().authorizeRequests()
        http.authorizeRequests()
                //.antMatchers("/users/**").access(SECURED_AUTORITY_ADMIN)
        		.antMatchers(SECURED_PATTERN).access(SECURED_AUTORITY_ADMIN)
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE)
                .antMatchers("/swagger-ui.html", "/beans/**", "/webjars/**", "/swagger-resources/**").permitAll()
                
                //.antMatchers("/customers/**").authenticated()
                .anyRequest().fullyAuthenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}