package com.services.direct.config.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.services.direct.security.CustomTokenEnhancer;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(SecurityConfig.class)
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter  {
    
	@Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
	
	@Value("${security.signing-key}")
	private String signingKey;
	
	@Resource(name = "userService")
	@Autowired
	UserDetailsService userDetailsService;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder oauthClientPasswordEncoder;
    

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(oauthClientPasswordEncoder);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }
// En cas de probleme il faut l'activer    
//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setAccessTokenValiditySeconds(60);
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }
    
    @Bean
	public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
	}
    
    @Bean
    public TokenEnhancer tokenEnhancer() {
    	return new CustomTokenEnhancer();
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // add retour perso 
    	 TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	    tokenEnhancerChain.setTokenEnhancers(
    	      Arrays.asList(tokenEnhancer(), accessTokenConverter()));
    	 endpoints.userDetailsService(userDetailsService);
    	// par default
    	endpoints.tokenStore(tokenStore())
    			 .tokenEnhancer(tokenEnhancerChain) // ajouter information sur l'utilisateur et la company
                 .accessTokenConverter(accessTokenConverter()) 
                 .authenticationManager(authenticationManager);
    	endpoints.getFrameworkEndpointHandlerMapping().setCorsConfigurations(getCorsConfig());
    }
    
    private Map<String, CorsConfiguration> getCorsConfig() {

        List<String> methodsList = Stream.of("GET", "POST", "PUT", "DELETE", "OPTIONS").collect(Collectors.toList());
        List<String> allowableOriginsList = Stream.of("*").collect(Collectors.toList());
        List<String> allowableHeadersList =
                Stream.of("Access-Control-Allow-Headers", "*")
                .collect(Collectors.toList());

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:8081", "http://localhost:8080", "http://soft-team.org", "http://ec2-52-15-124-186.us-east-2.compute.amazonaws.com:8080", "http://soft-team.fr"));
        config.setAllowedOrigins(allowableOriginsList);
        config.setAllowedMethods(methodsList);
        config.setAllowedHeaders(allowableHeadersList);

        Map<String, CorsConfiguration> corsConfigMap = new HashMap<>();
        corsConfigMap.put("/oauth/token", config);

        return corsConfigMap;
    }
}
