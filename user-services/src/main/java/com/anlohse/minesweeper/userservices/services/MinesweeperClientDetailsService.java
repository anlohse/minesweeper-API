package com.anlohse.minesweeper.userservices.services;

import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class MinesweeperClientDetailsService implements ClientDetailsService {

    ClientDetailsService delegate;

    public MinesweeperClientDetailsService() throws Exception {
        delegate = new InMemoryClientDetailsServiceBuilder()
            .withClient("writer")
                .authorizedGrantTypes("password")
                .secret("7caVEdmIlrnneDl5qWeh0IEhzc9ZGGBaHbKmEgwQaGc=")
                .scopes("user")
                .accessTokenValiditySeconds(604800)
                .and().build();
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return delegate.loadClientByClientId(clientId);
    }
}
