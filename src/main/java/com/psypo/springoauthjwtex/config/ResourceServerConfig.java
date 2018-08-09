package com.psypo.springoauthjwtex.config;

import com.psypo.springoauthjwtex.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Rimantas Jacikevičius on 18.8.5.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServerConfig.class);

    private final DefaultTokenServices tokenServices;
    private final UserDetailsServiceImpl userDetailsService;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.resource.id}")
    private String resourceId;

    @Autowired
    public ResourceServerConfig(DefaultTokenServices tokenServices, UserDetailsServiceImpl userDetailsService) {
        super();
        this.tokenServices = tokenServices;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated();
    }
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
////                .anonymous().disable()
//                .authorizeRequests()
////                .anyRequest().permitAll();
////                .antMatchers("/**").authenticated()
////                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/oauth/**").permitAll();
////                .antMatchers("/oauth/**").authenticated();
//
//    }

    @Bean
    public UserAuthenticationConverter userAuthenticationConverter () {
        DefaultUserAuthenticationConverter defaultUAC = new DefaultUserAuthenticationConverter();
        defaultUAC.setUserDetailsService(userDetailsService);
        return defaultUAC;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {

        LOGGER.info("Initializing JWT with public key: " + publicKey);

        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);

        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

}
