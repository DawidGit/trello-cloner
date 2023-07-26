package com.widulinski.trellocloner.domain;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public final class TrelloGiverAdminUser extends AbstractTrelloUser {

    @Value("${trello.giver.token}")
    private String token;
    @Value("${trello.giver.apikey}")
    private String key;
    @Value("${trello.giver.email}")
    private String email;

    @Override
    public String getAPIToken() {
        return token;
    }

    @Override
    public String getAPIKey() {
        return null;
    }

    @Override
    public String getEmail() {
        return email;
    }
}
