package com.psypo.springoauthjwtex.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by Rimantas Jacikevičius on 18.8.5.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final ClientDetailsService clientDetailsService;
    private final JwtAccessTokenConverter accessTokenConverter;
    private final TokenStore tokenStore;
    private final DefaultTokenServices tokenServices;

    @Autowired
    public AuthorizationServerConfig(@Qualifier("userDetailsService") UserDetailsService userDetailsService, AuthenticationManager authenticationManager, @Qualifier("clientService") ClientDetailsService clientDetailsService, JwtAccessTokenConverter accessTokenConverter, TokenStore tokenStore, DefaultTokenServices tokenServices) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.clientDetailsService = clientDetailsService;
        this.accessTokenConverter = accessTokenConverter;
        this.tokenStore = tokenStore;
        this.tokenServices = tokenServices;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * Defines the authorization and token endpoints and the token services
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(tokenStore)
                .tokenServices(tokenServices)
                .accessTokenConverter(accessTokenConverter);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}
