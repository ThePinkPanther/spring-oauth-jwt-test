package com.psypo.springoauthjwtex.service;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rimantas Jacikevičius on 18.8.5.
 */
@Service("clientService")
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final Map<String, ClientDetails> clients = new HashMap<>();

    public void addClient(ClientDetails client) {
        clients.put(client.getClientId(), client);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = clients.get(clientId);
        if (details == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return details;
    }

}
