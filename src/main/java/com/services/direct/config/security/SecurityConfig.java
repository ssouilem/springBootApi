package com.services.direct.config.security;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Resource(name = "userService")
	UserDetailsService userDetailsService;

//	@Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
//	/* todo delete */
//	@Autowired
//    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(encoder());
//    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable().csrf()
        		//.disable()
        		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/oauth/token").permitAll()
                //.antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
                //.antMatchers("/users/**").access("hasAuthority('ROLE_ADMIN')")
                //.anyRequest().access("hasAuthority('ROLE_ADMIN')")
                //.anyRequest().permitAll()
                //.and()
                // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                //.and()
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                .and()
		        .httpBasic();
        		
    }
    
/**
 * tokenStore bloc DataBase save token and autorise multi token
 */
//    @Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("souilem");
//        return converter;
//	}
//    
//    @Bean
//	public TokenStore tokenStore() {
//		return new JwtTokenStore(accessTokenConverter());
//	}

    /**
     * a verifier l'interet de garder les fonctions ci-dessous
     */
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//       // http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)	
//        		http.csrf().disable()
//                .cors().disable()
//                //.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint() {}).and()
//                //.authenticationProvider(getProvider())
//                .formLogin()
//                .loginProcessingUrl("/login")
//               // .successHandler(new AuthentificationLoginSuccessHandler())
//               // .failureHandler(new SimpleUrlAuthenticationFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                //.logoutSuccessHandler(new AuthentificationLogoutSuccessHandler())
//                .invalidateHttpSession(true)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/swagger-ui.html", "/webjars/**", "/swagger-resources/**").permitAll()
//                .antMatchers("/user").authenticated()
//                .anyRequest().permitAll();
//    }

    
    @Override
    public void configure(WebSecurity web) throws Exception {
      // Allow swagger to be accessed without authentication
      web.ignoring().antMatchers("/v2/api-docs")//
          .antMatchers("/swagger-resources/**")//
          .antMatchers("/swagger-ui.html")//
          .antMatchers("/configuration/**")//
          .antMatchers("/webjars/**")//
          .antMatchers("/public")
          .antMatchers("/beans/**")
          .antMatchers( HttpMethod.OPTIONS, "/**" )
          .antMatchers("/h2-console/**/**");
          // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
          
    }
    

//	@Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }



}
