package com.psypo.springoauthjwtex;

import com.psypo.springoauthjwtex.model.User;
import com.psypo.springoauthjwtex.service.ClientDetailsServiceImpl;
import com.psypo.springoauthjwtex.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Arrays;

/**
 * Created by Rimantas Jacikevičius on 18.7.31.
 */

@SpringBootApplication()
public class Application implements CommandLineRunner {

    private final ClientDetailsServiceImpl clientDetailsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;

    @Autowired
    public Application(ClientDetailsServiceImpl clientDetailsService, UserDetailsServiceImpl userDetailsService, PasswordEncoder encoder) {
        this.clientDetailsService = clientDetailsService;
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        BaseClientDetails client = new BaseClientDetails();
        client.setClientId("client");
        client.setAuthorizedGrantTypes(Arrays.asList("client_credentials", "password", "refresh_token", "authorization_code"));
        client.setScope(Arrays.asList("read", "write"));
        client.setResourceIds(Arrays.asList("oauth2-resource"));
        client.setClientSecret(encoder.encode("secret"));
        client.setRefreshTokenValiditySeconds(99);
        client.setAccessTokenValiditySeconds(99);
        clientDetailsService.addClient(client);

        User user = new User();
        user.setId("1");
        user.setUsername("user");
        user.addAuthority(new User.Authority("authorised"));
        user.setPassword(encoder.encode("passwd"));
    }

}